package br.com.rekome.services;

import java.io.IOException;
import java.time.temporal.ChronoUnit;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.PutObjectResult;

import br.com.rekome.entities.User;
import br.com.rekome.interfaces.CloudProviderService;
import br.com.rekome.utils.AwsUtils;
import br.com.rekome.utils.DateUtils;
import br.com.rekome.utils.EntitiesUtils;
import software.amazon.awssdk.core.SdkBytes;
import software.amazon.awssdk.services.rekognition.RekognitionClient;
import software.amazon.awssdk.services.rekognition.model.AssociateFacesRequest;
import software.amazon.awssdk.services.rekognition.model.AssociateFacesResponse;
import software.amazon.awssdk.services.rekognition.model.CompareFacesRequest;
import software.amazon.awssdk.services.rekognition.model.CompareFacesResponse;
import software.amazon.awssdk.services.rekognition.model.CreateCollectionRequest;
import software.amazon.awssdk.services.rekognition.model.CreateUserRequest;
import software.amazon.awssdk.services.rekognition.model.FaceRecord;
import software.amazon.awssdk.services.rekognition.model.Image;
import software.amazon.awssdk.services.rekognition.model.IndexFacesRequest;
import software.amazon.awssdk.services.rekognition.model.IndexFacesResponse;
import software.amazon.awssdk.services.rekognition.model.QualityFilter;
import software.amazon.awssdk.services.rekognition.model.S3Object;
import software.amazon.awssdk.services.rekognition.model.UnsuccessfulFaceAssociation;

@Service
@Profile("aws")
public class AwsService implements CloudProviderService {

	private static final Logger LOGGER = LoggerFactory.getLogger(AwsService.class);

	private final AmazonS3 awsS3;

	private final UserTermsService userTermsService;

	private final RekognitionClient rekognitionClient;

	@Value("${app.properties.aws-bucket}")
	private String bucketName;
	
	@Value("${app.properties.face-similarity-threshold}")
	private Float faceSimilarityThreshold;
	
	public AwsService(AmazonS3 awsS3, UserTermsService userTermsService, RekognitionClient rekognitionClient) {
		this.awsS3 = awsS3;
		this.userTermsService = userTermsService;
		this.rekognitionClient = rekognitionClient;
	}

	@Override
	public void createUser(User createdUser, MultipartFile file) throws Exception {

		this.insertUserImageCreationS3(createdUser, file);
		String collectionId = this.createCollection(createdUser);
		String faceId = this.indexFaces(file, collectionId, createdUser);
		String userId = this.createUser(collectionId, createdUser.getEmail());

		this.associeteFaces(collectionId, faceId, userId);
	}

	private void associeteFaces(String userCollectionId, String faceId, String userId) throws Exception {
		var associeteFacesRequest = AssociateFacesRequest.builder().collectionId(userCollectionId).faceIds(faceId)
				.userId(userId).build();

		AssociateFacesResponse associateFacesResponse = rekognitionClient.associateFaces(associeteFacesRequest);

		// verifica se a face foi associada.
		this.anyUnassociateFace(associateFacesResponse.unsuccessfulFaceAssociations(), faceId);

		LOGGER.info("face: {} has associated with successful to the user");
	}

	private void anyUnassociateFace(List<UnsuccessfulFaceAssociation> unsuccessfulFaceAssociations, String faceId)
			throws Exception {
		LOGGER.info("Verify if the face: {} has any failure in association.");
		unsuccessfulFaceAssociations.stream().filter(fail -> fail.faceId().equals(faceId)).findFirst()
				.ifPresent(fail -> {
					throw new RuntimeException(fail.reasonsAsStrings().get(0));
				});
	}

	private String indexFaces(MultipartFile file, String collectionId, User user) throws IOException {
		SdkBytes sourceBytes = SdkBytes.fromInputStream(file.getInputStream());

		var image = Image.builder().bytes(sourceBytes).build();

		IndexFacesRequest facesRequest = IndexFacesRequest.builder().collectionId(collectionId).image(image)
				.maxFaces(AwsUtils.MAX_FACES_INDEX_FACE).qualityFilter(QualityFilter.AUTO).build();

		IndexFacesResponse facesResponse = rekognitionClient.indexFaces(facesRequest);

		FaceRecord faceRecord = facesResponse.faceRecords().get(0);

		this.userTermsService.addFaceRecord(faceRecord, user);

		return faceRecord.face().faceId();

	}

	private String createCollection(User createdUser) {
		LOGGER.debug("Start create organization");
		var collectionId = AwsUtils.generateCollectionId(createdUser.getEmail());

		CreateCollectionRequest request = CreateCollectionRequest.builder().collectionId(collectionId).build();

		rekognitionClient.createCollection(request);

		userTermsService.addCollectionId(collectionId, createdUser);

		return collectionId;
	}

	private PutObjectResult insertUserImageCreationS3(User userOperation, MultipartFile file) throws Exception {
		try {
			LOGGER.debug("Insert user image in S3 Bucket to user {}", userOperation.getUuid());
			var bucketKey = EntitiesUtils.generateUUID();

			ObjectMetadata metadata = new ObjectMetadata();
			metadata.setContentType("multipart/form-data");

			metadata.addUserMetadata("userId", userOperation.getUuid());

			// Expiração da imagem em dois anos
			metadata.setExpirationTime(DateUtils.expirationTime(2, ChronoUnit.YEARS));

			var request = new PutObjectRequest(bucketName, bucketKey, file.getInputStream(), metadata);

			PutObjectResult putObject = awsS3.putObject(request);

			userTermsService.addBucketKey(bucketKey, userOperation);

			return putObject;
		} catch (Exception e) {
			throw e;
		}
	}

	private String createUser(String collectionId, String userId) {
		CreateUserRequest request = CreateUserRequest.builder().collectionId(collectionId).userId(userId).build();

		rekognitionClient.createUser(request);
		return userId;
	}

	@Override
	public void takeAttendance(User user, MultipartFile file) throws Exception {
		try {

			var imageBucketKey = userTermsService.getImagePath(user);
		
			byte[] imageBytes = file.getBytes();
			SdkBytes sdkBytes = SdkBytes.fromByteArray(imageBytes);

			Image sourceImage = Image.builder().bytes(sdkBytes).build();

			S3Object userImageObject = S3Object.builder().bucket(bucketName).name(imageBucketKey).build();
			
			Image targetImage = Image.builder().s3Object(userImageObject).build();

			CompareFacesRequest compareFacesRequest = CompareFacesRequest.builder()
					.sourceImage(sourceImage)
	                .targetImage(targetImage)
					.similarityThreshold(faceSimilarityThreshold)
					.build();

			CompareFacesResponse compareFacesResponse = rekognitionClient.compareFaces(compareFacesRequest);

			var facesMatched = compareFacesResponse.faceMatches();

			if(facesMatched.isEmpty() || compareFacesResponse.hasUnmatchedFaces()) {
				throw new RuntimeException("Image face don't matches with the user face.");
			}
			
		} catch (Exception e) {
			throw e;
		}

	}
}