package com.econetwireless.epay.business.services.impl;

import com.econetwireless.epay.dao.requestpartner.api.RequestPartnerDao;
import com.econetwireless.epay.domain.RequestPartner;
import com.econetwireless.utils.enums.ResponseCode;
import com.econetwireless.utils.execeptions.EpayException;
import junit.framework.TestCase;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class PartnerCodeValidatorImplTest extends TestCase {
    private RequestPartnerDao requestPartnerDao;
    private PartnerCodeValidatorImpl partnerCodeValidator;

    public void setUp() throws Exception {
        super.setUp();
        requestPartnerDao = mock(RequestPartnerDao.class);
        partnerCodeValidator = new PartnerCodeValidatorImpl(requestPartnerDao);
    }

    public void testThatIfPartnerCodeExistsItsValid() {
        RequestPartner requestPartner = new RequestPartner();
        String partnerCode = "buddie";
        when(requestPartnerDao.findByCode(partnerCode)).thenReturn(requestPartner);
        assertTrue(partnerCodeValidator.validatePartnerCode(partnerCode));
    }

    public void testThatIfPartnerCodeDoeNotExistsItsInvalid() {
        String partnerCode = "buddie";
        when(requestPartnerDao.findByCode(partnerCode)).thenReturn(null);
        try {
            assertTrue(partnerCodeValidator.validatePartnerCode(partnerCode));
            fail("EpayException");
        } catch (EpayException expect) {
            assertEquals(expect.getResponseCode(), ResponseCode.INVALID_REQUEST);
        }
    }
}
