package kr.co.vibevillage.common;

import kr.co.vibevillage.usedBoard.model.UsedBoardImageDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Component
public class UploadFile {

	// UPLOAD_PATH에는 이미지를 담을 경로를 써주세요
	@Value("${EUN_UPLOAD_PATH}")
	private String UPLOAD_PATH;

	// 메인이미지와 프리뷰이미지를 구분
	public List<UsedBoardImageDto> upload(List<MultipartFile> mainImage, List<MultipartFile> previewImages) {
		List<UsedBoardImageDto> totalImageDtos = new ArrayList<>();
		if(!mainImage.isEmpty()){
		List<UsedBoardImageDto> mainImageDtos = mainImage.stream()
				.map(file -> uploadSingleFile(file, "MAIN"))
				.collect(Collectors.toList());
		totalImageDtos.addAll(mainImageDtos);
		}
		if(previewImages !=null && !previewImages.isEmpty()){
			List<UsedBoardImageDto> previewImageDtos = previewImages.stream()
					.map(file -> uploadSingleFile(file, "PREVIEW"))
					.collect(Collectors.toList());
			totalImageDtos.addAll(previewImageDtos);
		}

		return totalImageDtos;
	}
	// 파일 이름 암호화 및 경로 지정 후 DTO에 담기
	private UsedBoardImageDto uploadSingleFile(MultipartFile upload, String imageType) {
		if (!upload.isEmpty()) {
			String originName = upload.getOriginalFilename();
			String extension = originName.substring(originName.lastIndexOf('.'));
			LocalDateTime now = LocalDateTime.now();
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyMMddHHmmss");
			String output = now.format(formatter);

			int stringLength = 8;
			String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
			Random random = new Random();
			String randomString = random.ints(stringLength, 0, characters.length())
					.mapToObj(characters::charAt)
					.map(Object::toString)
					.collect(Collectors.joining());

			String fileName = output + "_" + randomString + extension;
			String filePathName = UPLOAD_PATH + fileName;
			Path filePath = Paths.get(filePathName);

			try {
				upload.transferTo(filePath);
				UsedBoardImageDto imageDto = new UsedBoardImageDto();
				imageDto.setUploadOriginName(originName);
				imageDto.setUploadUniqueName(fileName);
				imageDto.setUploadImagePath(UPLOAD_PATH);
				imageDto.setUploadImageType(imageType); // Set the image type here
				return imageDto;
			} catch (IllegalStateException | IOException e) {
				System.out.println("오류");
				e.printStackTrace();
			}
		}
		return null;
	}
}
