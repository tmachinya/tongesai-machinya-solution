package com.econetwireless.epay.business.services.impl;

import com.econetwireless.epay.business.integrations.api.ChargingPlatform;
import com.econetwireless.epay.dao.subscriberrequest.api.SubscriberRequestDao;
import com.econetwireless.epay.domain.SubscriberRequest;
import com.econetwireless.utils.constants.SystemConstants;
import com.econetwireless.utils.enums.ResponseCode;
import com.econetwireless.utils.messages.AirtimeBalanceResponse;
import com.econetwireless.utils.pojo.INBalanceResponse;
import junit.framework.TestCase;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

public class EnquiriesServiceImplTest extends TestCase {
    private ChargingPlatform chargingPlatform;
    private SubscriberRequestDao subscriberRequestDao;
    private  EnquiriesServiceImpl enquiriesService;
    public void setUp() throws Exception {
        super.setUp();
        chargingPlatform = mock(ChargingPlatform.class);
        subscriberRequestDao = mock(SubscriberRequestDao.class);
        enquiriesService = new EnquiriesServiceImpl(chargingPlatform,subscriberRequestDao);
    }

    public void testUpdateSubscriberRequestStatusWhenEnquireIsSuccessful() {
        String partnerCode= "buddie";
        String msisdn = "0776194382";
        SubscriberRequest subscriberRequest = new SubscriberRequest();
        INBalanceResponse inBalanceResponse = new INBalanceResponse();
        inBalanceResponse.setMsisdn(msisdn);
        inBalanceResponse.setAmount(100.0);
        inBalanceResponse.setNarrative("Your balance is ");
        inBalanceResponse.setResponseCode(ResponseCode.SUCCESS.getCode());

        when(subscriberRequestDao.save(any(SubscriberRequest.class))).thenReturn(subscriberRequest);
        when(chargingPlatform.enquireBalance(partnerCode,msisdn)).thenReturn(inBalanceResponse);
        AirtimeBalanceResponse airtimeBalanceResponse = enquiriesService.enquire(partnerCode,msisdn);
        assertEquals(SystemConstants.STATUS_SUCCESSFUL,subscriberRequest.getStatus());

    }

    public void testUpdateSubscriberRequestBalanceAfterWhenEnquireIsSuccessful() {
        String partnerCode= "buddie";
        String msisdn = "0776194382";
        SubscriberRequest subscriberRequest = new SubscriberRequest();
        INBalanceResponse inBalanceResponse = new INBalanceResponse();
        inBalanceResponse.setMsisdn(msisdn);
        inBalanceResponse.setAmount(100.0);
        inBalanceResponse.setNarrative("Your balance is ");
        inBalanceResponse.setResponseCode(ResponseCode.SUCCESS.getCode());

        when(subscriberRequestDao.save(any(SubscriberRequest.class))).thenReturn(subscriberRequest);
        when(chargingPlatform.enquireBalance(partnerCode,msisdn)).thenReturn(inBalanceResponse);
        AirtimeBalanceResponse airtimeBalanceResponse = enquiriesService.enquire(partnerCode,msisdn);
        assertEquals(inBalanceResponse.getAmount(),subscriberRequest.getBalanceAfter());
    }

    public void testUpdateSubscriberRequestBalanceBeforeWhenEnquireIsSuccessful() {
        String partnerCode= "buddie";
        String msisdn = "0776194382";
        SubscriberRequest subscriberRequest = new SubscriberRequest();
        INBalanceResponse inBalanceResponse = new INBalanceResponse();
        inBalanceResponse.setMsisdn(msisdn);
        inBalanceResponse.setAmount(100.0);
        inBalanceResponse.setNarrative("Your balance is ");
        inBalanceResponse.setResponseCode(ResponseCode.SUCCESS.getCode());

        when(subscriberRequestDao.save(any(SubscriberRequest.class))).thenReturn(subscriberRequest);
        when(chargingPlatform.enquireBalance(partnerCode,msisdn)).thenReturn(inBalanceResponse);
        AirtimeBalanceResponse airtimeBalanceResponse = enquiriesService.enquire(partnerCode,msisdn);
        assertEquals(inBalanceResponse.getAmount(),subscriberRequest.getBalanceBefore());
    }

    public void testUpdateSubscriberRequestStatusWhenEnquireIsUnSuccessful() {
        String partnerCode= "buddie";
        String msisdn = "0776194382";
        SubscriberRequest subscriberRequest = new SubscriberRequest();
        INBalanceResponse inBalanceResponse = new INBalanceResponse();
        inBalanceResponse.setMsisdn(msisdn);
        inBalanceResponse.setAmount(100.0);
        inBalanceResponse.setNarrative("Your balance is ");
        inBalanceResponse.setResponseCode(ResponseCode.FAILED.getCode());

        when(subscriberRequestDao.save(any(SubscriberRequest.class))).thenReturn(subscriberRequest);
        when(chargingPlatform.enquireBalance(partnerCode,msisdn)).thenReturn(inBalanceResponse);
        AirtimeBalanceResponse airtimeBalanceResponse = enquiriesService.enquire(partnerCode,msisdn);
        assertEquals(SystemConstants.STATUS_FAILED,subscriberRequest.getStatus());

    }

    public void testThatCorrectResponseCodeIsReturned() {
        String partnerCode= "buddie";
        String msisdn = "0776194382";
        SubscriberRequest subscriberRequest = new SubscriberRequest();
        INBalanceResponse inBalanceResponse = new INBalanceResponse();
        inBalanceResponse.setMsisdn(msisdn);
        inBalanceResponse.setAmount(100.0);
        inBalanceResponse.setNarrative("Your balance is ");
        inBalanceResponse.setResponseCode(ResponseCode.SUCCESS.getCode());

        when(subscriberRequestDao.save(any(SubscriberRequest.class))).thenReturn(subscriberRequest);
        when(chargingPlatform.enquireBalance(partnerCode,msisdn)).thenReturn(inBalanceResponse);
        AirtimeBalanceResponse airtimeBalanceResponse = enquiriesService.enquire(partnerCode,msisdn);
        assertEquals(inBalanceResponse.getResponseCode(),airtimeBalanceResponse.getResponseCode());

    }

    public void testThatCorrectNarrativeIsReturned() {
        String partnerCode= "buddie";
        String msisdn = "0776194382";
        SubscriberRequest subscriberRequest = new SubscriberRequest();
        INBalanceResponse inBalanceResponse = new INBalanceResponse();
        inBalanceResponse.setMsisdn(msisdn);
        inBalanceResponse.setAmount(100.0);
        inBalanceResponse.setNarrative("Your balance is ");
        inBalanceResponse.setResponseCode(ResponseCode.SUCCESS.getCode());

        when(subscriberRequestDao.save(any(SubscriberRequest.class))).thenReturn(subscriberRequest);
        when(chargingPlatform.enquireBalance(partnerCode,msisdn)).thenReturn(inBalanceResponse);
        AirtimeBalanceResponse airtimeBalanceResponse = enquiriesService.enquire(partnerCode,msisdn);
        assertEquals(inBalanceResponse.getNarrative(),airtimeBalanceResponse.getNarrative());
    }

    public void testThatCorrectMsisdnIsReturned() {
        String partnerCode= "buddie";
        String msisdn = "0776194382";
        SubscriberRequest subscriberRequest = new SubscriberRequest();
        INBalanceResponse inBalanceResponse = new INBalanceResponse();
        inBalanceResponse.setMsisdn(msisdn);
        inBalanceResponse.setAmount(100.0);
        inBalanceResponse.setNarrative("Your balance is ");
        inBalanceResponse.setResponseCode(ResponseCode.SUCCESS.getCode());

        when(subscriberRequestDao.save(any(SubscriberRequest.class))).thenReturn(subscriberRequest);
        when(chargingPlatform.enquireBalance(partnerCode,msisdn)).thenReturn(inBalanceResponse);
        AirtimeBalanceResponse airtimeBalanceResponse = enquiriesService.enquire(partnerCode,msisdn);
        assertEquals(inBalanceResponse.getMsisdn(),airtimeBalanceResponse.getMsisdn());

    }

    public void testThatCorrectAmountIsReturned() {
        String partnerCode= "buddie";
        String msisdn = "0776194382";
        SubscriberRequest subscriberRequest = new SubscriberRequest();
        INBalanceResponse inBalanceResponse = new INBalanceResponse();
        inBalanceResponse.setMsisdn(msisdn);
        inBalanceResponse.setAmount(100.0);
        inBalanceResponse.setNarrative("Your balance is ");
        inBalanceResponse.setResponseCode(ResponseCode.SUCCESS.getCode());

        when(subscriberRequestDao.save(any(SubscriberRequest.class))).thenReturn(subscriberRequest);
        when(chargingPlatform.enquireBalance(partnerCode,msisdn)).thenReturn(inBalanceResponse);
        AirtimeBalanceResponse airtimeBalanceResponse = enquiriesService.enquire(partnerCode,msisdn);
        assertEquals(inBalanceResponse.getAmount(),airtimeBalanceResponse.getAmount());

    }
}
