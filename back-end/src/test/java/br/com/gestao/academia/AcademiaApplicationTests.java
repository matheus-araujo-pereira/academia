package br.com.gestao.academia;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.system.CapturedOutput;
import org.springframework.boot.test.system.OutputCaptureExtension;
import org.junit.jupiter.api.extension.ExtendWith;

@SpringBootTest
@ExtendWith(OutputCaptureExtension.class)
class AcademiaApplicationTests {

	@Test
	void contextLoads() {
	}

	@Test
	void testMain(CapturedOutput output) {
		AcademiaApplication.main(new String[] {});
		assertTrue(output.getOut().contains("Started AcademiaApplication"));
	}
}
