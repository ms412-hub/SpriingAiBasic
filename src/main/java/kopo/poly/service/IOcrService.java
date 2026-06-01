package kopo.poly.service;

import kopo.poly.dto.OcrDTO;

public interface IOcrService {

    // 인터페이스는 인터페이스를 구현하는 자바 객체들의 공통 상수 설정할 때도 활용됨
    // 학습 모델 파일이 존재하는 폴더 경로 지정
    String modelFile = "C:/model/tessdata";

    // 이미지 파일로부터 문자 읽어 오기
    // (이곳은 껍데기이므로 함수 이름, 매개변수, 리턴 타입만 정의합니다)
    OcrDTO getReadforImageText(OcrDTO pDTO) throws Exception;

}