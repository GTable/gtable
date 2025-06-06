package com.example.gtable.global.s3;

import java.io.InputStream;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectMetadata;

import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class S3Service {
	private final AmazonS3Client amazonS3Client;

	@Value("${cloud.aws.s3.bucket}")
	private String bucket;

	public record S3UploadResult(String key, String url) {
	}

	@Bulkhead(name = "s3UploadBulkhead", type = Bulkhead.Type.THREADPOOL)
	@Async("s3UploadExecutor")
	public CompletableFuture<S3UploadResult> upload(String type, Long refId, MultipartFile file) {
		try (InputStream inputStream = file.getInputStream()) {
			String key = createFileKey(type, refId, file.getOriginalFilename());
			ObjectMetadata metadata = new ObjectMetadata();
			metadata.setContentLength(file.getSize());

			amazonS3Client.putObject(bucket, key, inputStream, metadata);
			String url = amazonS3Client.getUrl(bucket, key).toString();

			return CompletableFuture.completedFuture(new S3UploadResult(key, url));
		} catch (Exception e) {
			throw new RuntimeException("S3 업로드 실패", e);
		}
	}

	public void delete(String filename) {
		try {
			amazonS3Client.deleteObject(bucket, filename);
		} catch (Exception e) {
			throw new RuntimeException("S3 파일 삭제 실패", e);
		}
	}

	private String createFileKey(String type, Long refId, String filename) {
		return type + "/" + refId + "/" + UUID.randomUUID() + "-" + filename;
	}
}
