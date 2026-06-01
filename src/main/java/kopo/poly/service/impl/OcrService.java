package kopo.poly.service.impl;

import kopo.poly.dto.OcrDTO;
import kopo.poly.service.IOcrService;
import lombok.extern.slf4j.Slf4j;
import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.File;

@Slf4j
@Service
public class OcrService implements IOcrService {

    @Override
    public OcrDTO getReadforImageText(OcrDTO pDTO) throws Exception {
        log.info(this.getClass().getName() + ".getReadforImageText Start!");

        // 1. 이미지가 들어있는 리소스를 가져옴 (resources 폴더 아래의 파일을 읽기 위함)
        ClassPathResource resource = new ClassPathResource("image/" + pDTO.getFileName());

        // 2. OCR 기능을 수행할 Tesseract 객체 생성
        ITesseract instance = new Tesseract();

        // 3. OCR 학습 모델 파일(tessdata)이 있는 경로 설정
        // 아까 인터페이스(IOcrService)에 적어둔 modelFile 변수를 사용함
        instance.setDatapath(IOcrService.modelFile);

        // 4. 한국어 학습 데이터를 사용하도록 설정 (기본은 영어)
        instance.setLanguage("kor");

        // 5. 이미지 파일로부터 텍스트 읽기 실행
        File imageFile = resource.getFile();
        String result = instance.doOCR(imageFile);

        // 6. 읽어온 텍스트를 DTO에 저장
        pDTO.setResult(result);

        log.info(this.getClass().getName() + ".getReadforImageText End!");

        return pDTO;
    }
}