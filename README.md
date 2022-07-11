### Developer Task 1 
### changes made
1.	I have added “this" to code = code; in the constructor of ResponseCode class in the econet-utils module and made the variable final on declaration

2.	Made MobileNumberUtils.LOGGER static since  Non-static field 'LOGGER' could not be referenced from a static context in MobileNumberUtils class under formatters package in econetwireless-utils module

3.	Changed all  ‘and’ operator to it’s actual ‘&&’ in RequestInterceptor Class located in electronic-payments-api module 

4.	Added @Autowired annotation in the EpayResource class to ensure dependency injection on the reportingProcessor and epayRequestProcessor instance variables.

5.	Replaced subscriberRequestDao.update with subscriberRequestDao.save in the enquire method in the EnquiriesServiceImpl class

6.	Replaced subscriberRequestDao.persist with subscriberRequestDao.save in the enquire method in the EnquiriesServiceImpl class


7.	Replaced subscriberRequestDao.update with subscriberRequestDao.save in the credit method in the CreditsServiceImpl class

8.	Replaced subscriberRequestDao.persist with subscriberRequestDao.save in the credit method in the CreditsServiceImpl class

9.	Changed @PreInsert to @PrePersist in the SubscriberRequest class

10.	Removed this(super) from PartnerCodeValidatorImpl in com.econetwireless.epay.business

11.	Added  EnquiriesServiceImplTest, PartnerCodeValidatorImplTest, CreditsServiceImplTest
