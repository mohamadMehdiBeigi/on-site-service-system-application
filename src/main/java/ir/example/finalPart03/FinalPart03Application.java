package ir.example.finalPart03;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class FinalPart03Application {
    public static void main(String[] args) {
        ConfigurableApplicationContext run = SpringApplication.run(FinalPart03Application.class, args);

//        AdminService adminService = run.getBean(AdminService.class);

//        System.out.println(adminService.findAllSpecialistsByCriteria("peciali", null, null, null, null));
//        System.out.println(adminService.findAllCustomerByCriteria("ust", null, null));

//        CustomerService customerService = run.getBean(CustomerService.class);
//        OrderService orderService = run.getBean(OrderService.class);
//        ServicesService servicesService = run.getBean(ServicesService.class);
//        SpecialistService specialistService = run.getBean(SpecialistService.class);
//        SubServiceService subServiceService = run.getBean(SubServiceService.class);
//        SuggestionService suggestionService = run.getBean(SuggestionService.class);
//        Customer customer = new Customer("aaa", "sss", "sss@gma.com", null, LocalDateTime.now(), 1.0);
//        Customer customer1 = new Customer();
//        customer1.setId(1L);
//        Order order = new Order(20000.0, "wwww", LocalDateTime.of(2025, 1, 1, 1, 1), null, "aaaa", null, null, customer1, null);
//        customerService.saveCustomer(customer);
/*
        Customer byEmailAndPassword = customerService.findByEmailAndPassword("sss@gma.com", "s3foKIH&");
        System.out.println(byEmailAndPassword.toString());
*/
//        System.out.println(customerService.findById(1L));
//        System.out.println(customer.toString());
//        specialistService.changePassword(16L, "1234mn", "1234mn");
//        orderService.saveCustomerOrder(order, 1000.0);
//        System.out.println(orderService.findById(1L));
//        System.out.println(orderService.findStartDateOfWork(1L));
//        orderService.changeOrderStatusWaitingForSelection(1L);
//        orderService.changeOrderStatusToComingToYourPlace(2L);
//        orderService.changeOrderStatusToStartedByCustomer(2L,1L );
//        orderService.changeOrderStatusToDone(2L, 1L);
//        Services services = new Services();
//        services.setServiceName("ascvr");
//        servicesService.saveService(services);
//        Specialist specialist = new Specialist("sas", "ssa", "ssas@gdg.com", null, LocalDateTime.now(), 0.0, SpecialistStatus.CONFIRMED, null, 0.0);
//        specialistService.saveSpecialist(specialist);
//        System.out.println(specialistService.findByEmailAndPassword("ssas@gdg.com", "@0Kf@5hR"));
//        System.out.println(specialistService.checkingSpecialistStatus(2L));
//        specialistService.confirmingSpecialStatus(2L);
//        Services services = new Services();
//        services.setId(1L);
//        SubServices subServices = new SubServices("asssdd", 1000.0, "sasa", services, null);
//        subServiceService.saveSubService(subServices);
//        subServiceService.updateBasePriceAndDescription(1L, 212344.0, "aaaaaaaaaaaqqq");
//        Services services = new Services();
//        services.setId(1L);
//        System.out.println(subServiceService.findAllByServiceId(1L));
//        System.out.println(allByServiceId.get(0));
//        Specialist specialist = new Specialist();
//        specialist.setId(2L);
//        Order order1 = new Order();
//        order1.setId(2L);
//        Suggestions suggestions = new Suggestions(LocalDateTime.now(), 10000000.0, LocalDateTime.of(2027, 1, 1, 1, 1), 1.5, specialist, order1);
//        suggestionService.saveSuggestionAndReturnOrderId(suggestions, LocalDateTime.of(2022, 1, 1, 1, 1), 100.0);
//        System.out.println(suggestionService.findAllByCustomerIdOrOrderBySuggestedPrice(1L));
//        System.out.println(suggestionService.findAllByCustomerIdOrOrderByTotalScores(1L));
//        Services services = new Services("cleaning2");
//        services.setId(1L);
//        SubServices subServices = new SubServices("cleaningKitchen", 1000.0, "no", services, null);
//        SubServices subServicesFinal = subServiceService.saveSubService(subServices);
//        specialistService.addSubServiceToSpecialist(16L, 11L);
//        System.out.println(orderService.findAvailableOrdersForSpecialist(77L));
//        orderService.findAllByOrderStatusAndSpecialistId(77L);
//        suggestionService.findAllByCustomerIdOrOrderByTotalScores(77L);
//        Specialist byId = specialistService.findById(2L);
//        System.out.println(byId.getAverageScores());

    }
}
