package kopo.poly;

import kopo.poly.dto.NlpDTO;
import kopo.poly.dto.OcrDTO;
import kopo.poly.dto.StudentDTO;
import kopo.poly.service.INlpService;
import kopo.poly.service.IOcrService;
import kopo.poly.service.IStudentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.*;

@Slf4j
@RequiredArgsConstructor
@SpringBootApplication
public class SpringAiBasicApplication implements CommandLineRunner {

    // @Service 정의된 자바 파일
    // Spring Frameworks 실행될 때, @Service 정의한 자바는 자동으로 메모리에 올림
    // 메모리에 올라간 OcrService 객체를 ocrService 변수에 객체를 넣어주기
    private final IOcrService ocrService;
    private final INlpService nlpService;

    public static void main(String[] args) {
        SpringApplication.run(SpringAiBasicApplication.class, args);
    }
    private final IStudentService studentService;

    @Override
    public void run(String... args) throws Exception {
        log.info("자바 프로그래밍 시작!!");

//        String filePath = "image"; // 문자열을 인식할 이미지 파일 경로
//        String fileName = "sample01.jpg"; // 문자열을 인식할 이미지 파일 이름
//
//        // 전달할 값(Parameter) 약자로 보통 변수명 앞에 p를 붙임 => pDTO
//        OcrDTO pDTO = new OcrDTO(); // OcrService의 함수에 정보를 전달할 DTO를 메모리에 올리기
//
//        pDTO.setFilePath(filePath);
//        pDTO.setFileName(fileName);
//
//        // 실행 결과(Result) 약자로 보통 변수명 앞에 r를 붙임 => rDTO
//        OcrDTO rDTO = ocrService.getReadforImageText(pDTO);
//
//        String result = rDTO.getResult(); // 인식된 문자열
//
//        log.info("인식된 문자열: ");
//        log.info(result);
//
//        log.info("자바 프로그래밍 종료!!");
//
//        log.info("--------------------------------------------------");
//        NlpDTO nlpDTO = nlpService.getPlainText(result);
//        log.info("형태소별 품사 분석 결과 : " + nlpDTO.getResult());
//
//        // =========================================================
//        // 가장 많이 언급된 단어 찾기 진행 (이미지 4, 5)
//        // =========================================================
//
//        // [1단계] 기사 글 중 명사만 추출
//        nlpDTO = nlpService.getNouns(result);
//        List<String> nouns = nlpDTO.getNouns();
//
//        // [2단계] 중복된 단어 제거 (Set 구조 사용)
//        Set<String> distinct = new HashSet<>(nouns);
//
//        log.info("중복 제거 수행 전 단어 수 : " + nouns.size());
//        log.info("중복 제거 수행 후 단어 수 : " + distinct.size());
//
//        // [3단계] 중복 제거된 단어로 명사 추출된 List로부터 횟수 찾고 Map으로 저장
//        Map<String, Integer> rMap = new HashMap<>();
//
//        for (String s : distinct) {
//            int count = Collections.frequency(nouns, s); // 단어 빈도수 구하기
//            rMap.put(s, count); // 단어와 빈도수를 Map 구조로 저장
//
//            // log.info(s + " : " + count); // 필요시 저장된 결과 개별 출력
//        }
//
//        // [4단계] Map 구조의 값(Value, 빈도수)에 따라 큰 숫자순(내림차순)으로 정렬
//        List<Map.Entry<String, Integer>> sortResult = new LinkedList<>(rMap.entrySet());
//
//        // Collections.sort와 람다식을 이용한 Value 기준 내림차순 정렬
//        Collections.sort(sortResult, (o1, o2) -> o2.getValue().compareTo(o1.getValue()));
//
//        log.info("가장 많이 사용된 단어는? : " + sortResult);

        StudentDTO pDTO; // 학생 등록, 수정, 삭제에 활용될 DTO
        List<StudentDTO> rList; // DB 조회 결과를 표현

// 학생 등록하기
        pDTO = new StudentDTO();

        pDTO.setUserId("hglee67");
        pDTO.setUserName("이협건");
        pDTO.setEmail("hglee67@kopo.ac.kr");
        pDTO.setAddr("서울");

        rList = studentService.insertStudent(pDTO);

        rList.forEach(dto -> {
            log.info("DB에 저장된 아이디 : " + dto.getUserId());
            log.info("DB에 저장된 이름 : " + dto.getUserName());
            log.info("DB에 저장된 이메일 : " + dto.getEmail());
            log.info("DB에 저장된 주소 : " + dto.getAddr());
        });
        // 2. 학생 수정하기
        pDTO = new StudentDTO();

        pDTO.setUserId("hglee67"); // PK 컬럼인 회원 아이디를 기준으로 데이터를 수정함
        pDTO.setUserName("이협건_수정");
        pDTO.setEmail("hglee67@kopo.ac.kr_수정");
        pDTO.setAddr("서울_수정");

        rList = studentService.updateStudent(pDTO);

        rList.forEach(dto -> {
            log.info("DB에 저장된 아이디 : " + dto.getUserId());
            log.info("DB에 저장된 이름 : " + dto.getUserName());
            log.info("DB에 저장된 이메일 : " + dto.getEmail());
            log.info("DB에 저장된 주소 : " + dto.getAddr());
        });
        // 학생 삭제하기
        pDTO = new StudentDTO();

        pDTO.setUserId("hglee67"); // PK 칼럼인 회원 아이디를 기준으로 데이터를 수정함

        rList = studentService.deleteStudent(pDTO);

        rList.forEach(dto -> {
            log.info("DB에 저장된 아이디 : " + dto.getUserId());
            log.info("DB에 저장된 이름 : " + dto.getUserName());
            log.info("DB에 저장된 이메일 : " + dto.getEmail());
            log.info("DB에 저장된 주소 : " + dto.getAddr());
        });

        log.info("자바 프로그래밍 종료!!");
    }
}