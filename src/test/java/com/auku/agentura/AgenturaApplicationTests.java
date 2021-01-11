package com.auku.agentura;

import com.auku.agentura.service.AgencyService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class AgenturaApplicationTests {

	@Autowired
	private AgencyService agencyService;

	@Test
	void contextLoads() {
		Assertions.assertEquals(0, 0);
	}

	@Test
	void testDbData() {
		var owners = agencyService.getOwners();
		var houses = agencyService.getHouses();
		var houseData = agencyService.getHouseData();

		Assertions.assertNotNull(owners);
		Assertions.assertNotNull(houses);
		Assertions.assertNotNull(houseData);

		var temp = agencyService.getOwners().get(0);
		var temp2 = agencyService.getHouseData().get(0);

		Assertions.assertNotEquals(temp, temp2);
	}

}
