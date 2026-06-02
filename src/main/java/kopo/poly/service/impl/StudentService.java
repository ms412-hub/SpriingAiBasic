package kopo.poly.service.impl;

import kopo.poly.dto.StudentDTO;
import kopo.poly.mapper.IStudentMapper;
import kopo.poly.service.IStudentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class StudentService implements IStudentService {

    // 2단계에서 구현한 Mapper 인터페이스 의존성 주입 (오라클 DB 연결용)
    private final IStudentMapper studentMapper;

    /**
     * 학생 정보를 DB에 등록한 후, 전체 학생 목록을 조회하여 반환하는 메서드
     *
     * @param pDTO 등록할 학생 정보를 담고 있는 DTO 객체
     * @return 전체 학생 목록 (등록 결과 포함)
     * @throws Exception 예외 처리
     */
    @Override
    public List<StudentDTO> insertStudent(StudentDTO pDTO) throws Exception {

        // 현재 실행 중인 클래스명을 로그에 출력하기 위해 저장
        String className = this.getClass().getName();
        log.info("{}.insertStudent Start!", className);

        // 1. DB에 이미 동일한 학생 아이디(USER_ID)가 존재하는지 중복 조회
        Optional<StudentDTO> res = Optional.ofNullable(
                studentMapper.getStudent(pDTO)
        );

        // 2. 조회 결과가 없으면 (즉, 중복되지 않으면) insert 쿼리 실행
        if (res.isEmpty()) {
            // DB에 동일한 아이디가 없으므로 새로 등록
            studentMapper.insertStudent(pDTO);
            log.info("학생 등록 완료 - ID: {}", pDTO.getUserId());
        } else {
            // 이미 존재하는 경우 등록하지 않음
            log.warn("학생 등록 실패 - 이미 존재하는 ID: {}", pDTO.getUserId());
        }

        // 3. 전체 학생 목록 다시 조회 (최신 데이터 갱신)
        // - getStudentList()가 null일 경우 빈 리스트 반환하여 NullPointerException 방지
        List<StudentDTO> rList = Optional.ofNullable(
                studentMapper.getStudentList()
        ).orElseGet(ArrayList::new);

        // 4. 메서드 종료 로그 출력
        log.info("{}.insertStudent End!", className);

        // 5. 전체 학생 목록 반환
        return rList;
    }
    /**
     * 학생 정보를 수정한 후, 전체 학생 목록을 조회하여 반환하는 메서드
     *
     * @param pDTO 수정할 학생 정보를 담고 있는 DTO 객체
     * @return 수정 후 전체 학생 목록
     * @throws Exception 예외 처리
     */
    @Override
    public List<StudentDTO> updateStudent(StudentDTO pDTO) throws Exception {

        // 현재 실행 중인 클래스 이름을 로그에 출력하기 위해 저장
        String className = this.getClass().getName();
        log.info("{}.updateStudent Start!", className);

        // 1. 학생 정보가 실제로 DB에 존재하는지 먼저 확인
        // -> userId가 존재하는지 SELECT 쿼리 실행
        // -> Optional로 감싸 null 발생 가능성 안전 처리
        Optional<StudentDTO> res = Optional.ofNullable(studentMapper.getStudent(pDTO));

        // 2. 조회 결과가 존재하면 (즉, 해당 ID의 학생이 있으면)
        if (res.isPresent()) {
            // 2-1. 실제 DB에 저장된 학생 정보가 존재하므로 update 쿼리 실행
            studentMapper.updateStudent(pDTO);

            // 2-2. 로그로 어떤 학생이 수정되었는지 확인
            log.info("{}님이 수정되었습니다.", pDTO.getUserId());
        } else {
            // 3. 조회 결과가 없으면 (해당 ID의 학생이 DB에 없으면)
            // -> update 쿼리 실행하지 않고 경고 로그 출력
            log.warn("수정 실패 - 존재하지 않는 회원입니다: {}", pDTO.getUserId());
        }

        // 4. 수정 이후 전체 학생 목록 다시 조회
        // -> studentMapper.getStudentList()가 null을 반환할 수 있으므로 Optional로 감싸고,
        // -> null이면 빈 ArrayList를 반환하여 NPE 방지
        List<StudentDTO> rList = Optional.ofNullable(
                studentMapper.getStudentList()
        ).orElseGet(ArrayList::new);

        // 5. 종료 로그 출력
        log.info("{}.updateStudent End!", className);

        // 6. 전체 학생 목록 반환
        return rList;
    }
    /**
     * 학생 정보를 DB에서 삭제한 후, 전체 학생 목록을 조회하여 반환하는 메서드
     * * @param pDTO 삭제할 학생 정보를 담고 있는 DTO (주로 userId 사용)
     * @return 삭제 후 전체 학생 목록
     * @throws Exception 예외 발생 시 처리
     */
    @Override
    public List<StudentDTO> deleteStudent(StudentDTO pDTO) throws Exception {

        // 현재 실행 중인 클래스명을 로그에 출력하기 위한 변수
        String className = this.getClass().getName();
        log.info("{}.deleteStudent Start!", className);

        // 1. DB에 해당 학생(userId)이 존재하는지 조회
        // -> 존재하지 않으면 delete 쿼리를 실행할 필요 없음
        Optional<StudentDTO> res = Optional.ofNullable(
                studentMapper.getStudent(pDTO)
        );
        // 2. 조회 결과가 존재하면 -> 삭제 쿼리 실행
        if (res.isPresent()) {
            // 해당 학생을 DB에서 삭제
            studentMapper.deleteStudent(pDTO);

            // 로그 출력: 어떤 학생이 삭제되었는지 명시
            log.info("{}님이 삭제되었습니다.", pDTO.getUserId());

        } else {
            // 3. 조회 결과가 없으면 -> 삭제 대상이 존재하지 않음
            log.warn("삭제 실패 - 존재하지 않는 회원입니다: {}", pDTO.getUserId());
        }

        // 4. 삭제 후 전체 학생 목록 재조회
        // -> 결과가 null이면 빈 리스트 반환하여 NPE 방지
        List<StudentDTO> rList = Optional.ofNullable(
                studentMapper.getStudentList()
        ).orElseGet(ArrayList::new);

        // 5. 로그: 삭제 작업 종료
        log.info("{}.deleteStudent End!", className);

        // 6. 전체 목록 반환
        return rList;
    }

}