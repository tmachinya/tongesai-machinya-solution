package com.econetwireless.epay.business.services.impl;

import com.econetwireless.epay.business.integrations.api.ChargingPlatform;
import com.econetwireless.epay.dao.subscriberrequest.api.SubscriberRequestDao;
import com.econetwireless.epay.domain.SubscriberRequest;
import com.econetwireless.utils.constants.SystemConstants;
import com.econetwireless.utils.enums.ResponseCode;
import com.econetwireless.utils.messages.AirtimeTopupRequest;
import com.econetwireless.utils.messages.AirtimeTopupResponse;
import com.econetwireless.utils.pojo.INCreditRequest;
import com.econetwireless.utils.pojo.INCreditResponse;
import junit.framework.TestCase;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CreditsServiceImplTest extends TestCase {
    private ChargingPlatform chargingPlatform;
    private SubscriberRequestDao subscriberRequestDao;
    private CreditsServiceImpl creditsService;

    public void setUp() throws Exception {
        super.setUp();
        chargingPlatform = mock(ChargingPlatform.class);
        subscriberRequestDao = mock(SubscriberRequestDao.class);
        creditsService = new CreditsServiceImpl(chargingPlatform, subscriberRequestDao);
    }

    public void testUpdateSubscriberRequestStatusWhenEnquireIsSuccessful() {
        AirtimeTopupRequest airtimeTopupRequest = createAirtimeTopupRequest();
        SubscriberRequest subscriberRequest = new SubscriberRequest();
        INCreditResponse inCreditResponse = new INCreditResponse();
        inCreditResponse.setMsisdn(airtimeTopupRequest.getMsisdn());
        inCreditResponse.setBalance(100.0);
        inCreditResponse.setNarrative("Your new balance is ");
        inCreditResponse.setResponseCode(ResponseCode.SUCCESS.getCode());

        when(subscriberRequestDao.save(any(SubscriberRequest.class))).thenReturn(subscriberRequest);
        when(chargingPlatform.creditSubscriberAccount(any(INCreditRequest.class))).thenReturn(inCreditResponse);
        AirtimeTopupResponse airtimeTopupResponse = creditsService.credit(airtimeTopupRequest);
        assertEquals(SystemConstants.STATUS_SUCCESSFUL, subscriberRequest.getStatus());

    }

    private AirtimeTopupRequest createAirtimeTopupRequest() {
        AirtimeTopupRequest airtimeTopupRequest = new AirtimeTopupRequest();
        airtimeTopupRequest.setAmount(100);
        airtimeTopupRequest.setMsisdn("0776194382");
        airtimeTopupRequest.setPartnerCode("Buddie");
        airtimeTopupRequest.setReferenceNumber("1256671771");
        return airtimeTopupRequest;
    }

    public void testUpdateSubscriberRequestStatusWhenEnquireIsUnSuccessful() {
        AirtimeTopupRequest airtimeTopupRequest = createAirtimeTopupRequest();
        SubscriberRequest subscriberRequest = new SubscriberRequest();
        INCreditResponse inCreditResponse = new INCreditResponse();
        inCreditResponse.setMsisdn(airtimeTopupRequest.getMsisdn());
        inCreditResponse.setBalance(100.0);
        inCreditResponse.setNarrative("Your new balance is ");
        inCreditResponse.setResponseCode(ResponseCode.FAILED.getCode());

        when(subscriberRequestDao.save(any(SubscriberRequest.class))).thenReturn(subscriberRequest);
        when(chargingPlatform.creditSubscriberAccount(any(INCreditRequest.class))).thenReturn(inCreditResponse);
        AirtimeTopupResponse airtimeTopupResponse = creditsService.credit(airtimeTopupRequest);
        assertEquals(SystemConstants.STATUS_FAILED, subscriberRequest.getStatus());

    }

    public void testUpdateSubscriberRequestBalanceAfterWhenEnquireIsSuccessful() {
        AirtimeTopupRequest airtimeTopupRequest = createAirtimeTopupRequest();
        SubscriberRequest subscriberRequest = new SubscriberRequest();
        INCreditResponse inCreditResponse = new INCreditResponse();
        inCreditResponse.setMsisdn(airtimeTopupRequest.getMsisdn());
        inCreditResponse.setBalance(120.0);
        inCreditResponse.setNarrative("Your new balance is ");
        inCreditResponse.setResponseCode(ResponseCode.SUCCESS.getCode());

        when(subscriberRequestDao.save(any(SubscriberRequest.class))).thenReturn(subscriberRequest);
        when(chargingPlatform.creditSubscriberAccount(any(INCreditRequest.class))).thenReturn(inCreditResponse);
        AirtimeTopupResponse airtimeTopupResponse = creditsService.credit(airtimeTopupRequest);
        assertEquals(inCreditResponse.getBalance(), subscriberRequest.getBalanceAfter());
    }

    public void testUpdateSubscriberRequestBalanceBeforeWhenEnquireIsSuccessful() {
        AirtimeTopupRequest airtimeTopupRequest = createAirtimeTopupRequest();
        SubscriberRequest subscriberRequest = new SubscriberRequest();
        subscriberRequest.setBalanceBefore(20.0);
        subscriberRequest.setAmount(airtimeTopupRequest.getAmount());
        INCreditResponse inCreditResponse = new INCreditResponse();
        inCreditResponse.setMsisdn(airtimeTopupRequest.getMsisdn());
        inCreditResponse.setBalance(120.0);
        inCreditResponse.setNarrative("Your new balance is ");
        inCreditResponse.setResponseCode(ResponseCode.SUCCESS.getCode());

        when(subscriberRequestDao.save(any(SubscriberRequest.class))).thenReturn(subscriberRequest);
        when(chargingPlatform.creditSubscriberAccount(any(INCreditRequest.class))).thenReturn(inCreditResponse);
        AirtimeTopupResponse airtimeTopupResponse = creditsService.credit(airtimeTopupRequest);
        double expectedBalanceBefore = inCreditResponse.getBalance() - subscriberRequest.getAmount();
        assertEquals(expectedBalanceBefore, subscriberRequest.getBalanceBefore());
    }


    public void testThatCorrectResponseCodeIsReturned() {
        AirtimeTopupRequest airtimeTopupRequest = createAirtimeTopupRequest();
        SubscriberRequest subscriberRequest = new SubscriberRequest();
        INCreditResponse inCreditResponse = new INCreditResponse();
        inCreditResponse.setMsisdn(airtimeTopupRequest.getMsisdn());
        inCreditResponse.setBalance(120.0);
        inCreditResponse.setNarrative("Your new balance is ");
        inCreditResponse.setResponseCode(ResponseCode.SUCCESS.getCode());

        when(subscriberRequestDao.save(any(SubscriberRequest.class))).thenReturn(subscriberRequest);
        when(chargingPlatform.creditSubscriberAccount(any(INCreditRequest.class))).thenReturn(inCreditResponse);
        AirtimeTopupResponse airtimeTopupResponse = creditsService.credit(airtimeTopupRequest);
        assertEquals(inCreditResponse.getResponseCode(), airtimeTopupResponse.getResponseCode());

    }

    public void testThatCorrectNarrativeIsReturned() {
        AirtimeTopupRequest airtimeTopupRequest = createAirtimeTopupRequest();
        SubscriberRequest subscriberRequest = new SubscriberRequest();
        INCreditResponse inCreditResponse = new INCreditResponse();
        inCreditResponse.setMsisdn(airtimeTopupRequest.getMsisdn());
        inCreditResponse.setBalance(120.0);
        inCreditResponse.setNarrative("Your new balance is ");
        inCreditResponse.setResponseCode(ResponseCode.SUCCESS.getCode());

        when(subscriberRequestDao.save(any(SubscriberRequest.class))).thenReturn(subscriberRequest);
        when(chargingPlatform.creditSubscriberAccount(any(INCreditRequest.class))).thenReturn(inCreditResponse);
        AirtimeTopupResponse airtimeTopupResponse = creditsService.credit(airtimeTopupRequest);
        assertEquals(inCreditResponse.getNarrative(), airtimeTopupResponse.getNarrative());
    }

    public void testThatCorrectMsisdnIsReturned() {
        AirtimeTopupRequest airtimeTopupRequest = createAirtimeTopupRequest();
        SubscriberRequest subscriberRequest = new SubscriberRequest();
        INCreditResponse inCreditResponse = new INCreditResponse();
        inCreditResponse.setMsisdn(airtimeTopupRequest.getMsisdn());
        inCreditResponse.setBalance(120.0);
        inCreditResponse.setNarrative("Your new balance is ");
        inCreditResponse.setResponseCode(ResponseCode.SUCCESS.getCode());

        when(subscriberRequestDao.save(any(SubscriberRequest.class))).thenReturn(subscriberRequest);
        when(chargingPlatform.creditSubscriberAccount(any(INCreditRequest.class))).thenReturn(inCreditResponse);
        AirtimeTopupResponse airtimeTopupResponse = creditsService.credit(airtimeTopupRequest);
        assertEquals(inCreditResponse.getMsisdn(), airtimeTopupResponse.getMsisdn());

    }

    public void testThatCorrectBalanceIsReturned() {
        AirtimeTopupRequest airtimeTopupRequest = createAirtimeTopupRequest();
        SubscriberRequest subscriberRequest = new SubscriberRequest();
        INCreditResponse inCreditResponse = new INCreditResponse();
        inCreditResponse.setMsisdn(airtimeTopupRequest.getMsisdn());
        inCreditResponse.setBalance(120.0);
        inCreditResponse.setNarrative("Your new balance is ");
        inCreditResponse.setResponseCode(ResponseCode.SUCCESS.getCode());

        when(subscriberRequestDao.save(any(SubscriberRequest.class))).thenReturn(subscriberRequest);
        when(chargingPlatform.creditSubscriberAccount(any(INCreditRequest.class))).thenReturn(inCreditResponse);
        AirtimeTopupResponse airtimeTopupResponse = creditsService.credit(airtimeTopupRequest);
        assertEquals(inCreditResponse.getBalance(), airtimeTopupResponse.getBalance());

    }
}
