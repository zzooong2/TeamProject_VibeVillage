package kr.co.vibevillage.common;

import kr.co.vibevillage.usedBoard.model.UsedBoardImageDto;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Component
public class UploadFile {

	private final String UPLOAD_PATH = "C:/DEV/IntelliJ/FINAL_PROJECT/TeamProject_VibeVillage/vibevillage/src/main/resources/static/uploadUsedImages/";

	public List<UsedBoardImageDto> upload(List<MultipartFile> uploads) {
		return uploads.stream().map(upload -> {
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
					imageDto.setUploadImageType(extension);
					return imageDto;
				} catch (IllegalStateException | IOException e) {
					System.out.println("오류");
					e.printStackTrace();
				}
			}
			return null;
		}).collect(Collectors.toList());
	}
}
