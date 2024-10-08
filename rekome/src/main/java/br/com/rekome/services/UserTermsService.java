package br.com.rekome.services;

import static br.com.rekome.enums.UserTermsEnum.BUCKET_KEY;
import static br.com.rekome.enums.UserTermsEnum.COLLECTION_ID;
import static br.com.rekome.enums.UserTermsEnum.FACE_RECORD;

import java.util.Collection;

import org.springframework.stereotype.Service;

import br.com.rekome.entities.User;
import br.com.rekome.entities.UserTerms;
import br.com.rekome.enums.UserTermsEnum;
import br.com.rekome.repository.UserTermsRepository;
import software.amazon.awssdk.services.rekognition.model.FaceRecord;

@Service
public class UserTermsService {
		
	private final UserTermsRepository repository;

	public UserTermsService(UserTermsRepository repository) {
		this.repository = repository;
	}

	public Collection<UserTerms> getByTermAndUser(UserTermsEnum bucketKey, User user) {
		return this.repository.findByTermAndUser(bucketKey, user);
	}
	
	public String getImagePath(User user){
		var collectionOfImages = this.getByTermAndUser(BUCKET_KEY, user);
		return collectionOfImages.stream().findFirst().orElseThrow().getValue();
	}
	
	public String getFaceId(User user ) {
		var collectionOfImages = this.getByTermAndUser(FACE_RECORD, user);
		return collectionOfImages.stream().findFirst().orElseThrow().getValue();
	}
	
	public UserTerms addBucketKey(String bucketKey, User user) {
		UserTerms term = new UserTerms();
		
		term.setKey(BUCKET_KEY);
		term.setUser(user);
		term.setValue(bucketKey);
		
		return this.save(term);
	}
	
	public UserTerms addCollectionId(String collectionId, User user) {
		UserTerms term = new UserTerms();
		
		term.setKey(COLLECTION_ID);
		term.setUser(user);
		term.setValue(collectionId);
		
		return this.save(term);
	}
	
	public UserTerms addFaceRecord(FaceRecord face, User user) {
		UserTerms term = new UserTerms();

		term.setKey(FACE_RECORD);
		term.setUser(user);
		term.setValue(face.toString());
		
		return this.save(term);
	}

	public UserTerms save(UserTerms terms) {
		return this.repository.save(terms);
	}
}
