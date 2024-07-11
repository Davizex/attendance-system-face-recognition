package br.com.rekome.services;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
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

import br.com.rekome.entities.User;
import br.com.rekome.interfaces.CloudProviderService;
import br.com.rekome.utils.AwsUtils;
import br.com.rekome.utils.UserUtils;
import software.amazon.awssdk.core.SdkBytes;
import software.amazon.awssdk.services.rekognition.RekognitionClient;
import software.amazon.awssdk.services.rekognition.model.AssociateFacesRequest;
import software.amazon.awssdk.services.rekognition.model.AssociateFacesResponse;
import software.amazon.awssdk.services.rekognition.model.CreateCollectionRequest;
import software.amazon.awssdk.services.rekognition.model.CreateCollectionResponse;
import software.amazon.awssdk.services.rekognition.model.CreateUserRequest;
import software.amazon.awssdk.services.rekognition.model.CreateUserResponse;
import software.amazon.awssdk.services.rekognition.model.FaceRecord;
import software.amazon.awssdk.services.rekognition.model.Image;
import software.amazon.awssdk.services.rekognition.model.IndexFacesRequest;
import software.amazon.awssdk.services.rekognition.model.IndexFacesResponse;
import software.amazon.awssdk.services.rekognition.model.QualityFilter;

@Service
@Profile("aws")
public class AwsRekognitionService implements CloudProviderService {

	private static final Logger LOGGER = LoggerFactory.getLogger(AwsRekognitionService.class);

	private final AmazonS3 awsS3;

	private final UserTermsService userTermsService;

	private final RekognitionClient rekognitionClient;

	@Value("${app.properties.aws-bucket}")
	private String bucketName;

	public AwsRekognitionService(AmazonS3 awsS3, UserTermsService userTermsService, RekognitionClient rekognitionClient,
			String bucketName) {
		this.awsS3 = awsS3;
		this.userTermsService = userTermsService;
		this.rekognitionClient = rekognitionClient;
		this.bucketName = bucketName;
	}

	@Override
	public void createUser(User createdUser, MultipartFile file) throws Exception {

		this.insertUserImageCreationS3(createdUser, file);
		var collectionId = this.createOrganization(createdUser);
		var faceId = this.indexFaces(file, collectionId, createdUser);
		var userId = this.createUser(collectionId, createdUser.getEmail());
		
		this.associeteFaces(collectionId, Arrays.asList(faceId) ,userId);
	}

	private void associeteFaces(String userCollectionId, List<String> facesId, String userId) {
		var associeteFacesRequest = AssociateFacesRequest.builder().collectionId(userCollectionId).faceIds(facesId)
				.userId(userId).build();

		AssociateFacesResponse associateFacesResponse = rekognitionClient.associateFaces(associeteFacesRequest);
		
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

	private String createOrganization(User createdUser) {
		LOGGER.debug("Start create organization");
		var collectionId = AwsUtils.generateCollectionId(createdUser.getEmail());

		CreateCollectionRequest request = CreateCollectionRequest.builder().collectionId(collectionId).build();

		CreateCollectionResponse createCollection = rekognitionClient.createCollection(request);

		userTermsService.addCollectionId(collectionId, createdUser);

		return collectionId;
	}

	public void insertUserImageCreationS3(User userOperation, MultipartFile file) throws Exception {
		try {
			var bucketKey = UserUtils.generateUUID();

			ObjectMetadata metadata = new ObjectMetadata();
			metadata.setContentType("multipart/form-data");
			metadata.addUserMetadata("userId", userOperation.getUuid());

			var request = new PutObjectRequest(bucketName, bucketKey, file.getInputStream(), metadata);

			awsS3.putObject(request);

			userTermsService.addBucketKey(bucketKey, userOperation);
		} catch (Exception e) {
			throw e;
		}
	}

	private String createUser(String collectionId, String userId) {

		CreateUserRequest request = CreateUserRequest.builder().collectionId(collectionId).userId(userId).build();

		rekognitionClient.createUser(request);
		return userId;
	}

}