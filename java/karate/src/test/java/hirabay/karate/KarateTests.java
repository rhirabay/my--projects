package hirabay.karate;

import com.intuit.karate.junit5.Karate;

class KarateTests {

	@Karate.Test
	Karate testSample() {
		return Karate.run().relativeTo(getClass());
	}

}
