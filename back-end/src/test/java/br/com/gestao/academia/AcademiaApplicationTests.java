package br.com.gestao.academia;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.system.CapturedOutput;
import org.springframework.boot.test.system.OutputCaptureExtension;

@ExtendWith(OutputCaptureExtension.class)
class AcademiaApplicationTests {

	@Test
	void testMain(CapturedOutput output) {
		AcademiaApplication.main(new String[] { "--server.port=0" });
	}
}
