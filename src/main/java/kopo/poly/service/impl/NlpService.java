package kopo.poly.service.impl;

import kopo.poly.dto.NlpDTO;
import kopo.poly.service.INlpService;
import kr.co.shineware.nlp.komoran.constant.DEFAULT_MODEL;
import kr.co.shineware.nlp.komoran.core.Komoran;
import kr.co.shineware.nlp.komoran.model.KomoranResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class NlpService implements INlpService {

    // Komoran 라이브러리에 포함된 학습모델 종류 선택 (Full : 학습량 많음 / Light : 학습량 적음)
    Komoran komoran = new Komoran(DEFAULT_MODEL.FULL);

    @Override
    public NlpDTO getPlainText(String text) {
        log.info(this.getClass().getName() + ".getPlainText Start!");

        KomoranResult komoranResult = komoran.analyze(text); // 인식된 문자열 분석 결과

        String result = komoranResult.getPlainText(); // 모든 단어마다 품사 태깅

        NlpDTO rDTO = new NlpDTO();
        rDTO.setResult(result);

        log.info(this.getClass().getName() + ".getPlainText End!");

        return rDTO;
    }

    @Override
    public NlpDTO getNouns(String text) {
        log.info(this.getClass().getName() + ".getNouns Start!");

        KomoranResult komoranResult = komoran.analyze(text); // 인식된 문자열 분석 결과

        List<String> nouns = komoranResult.getNouns(); // NNG(일반명사), NNP(고유명사) 품사만 추출

        NlpDTO rDTO = new NlpDTO();
        rDTO.setNouns(nouns);

        log.info(this.getClass().getName() + ".getNouns End!");

        return rDTO;
    }
}