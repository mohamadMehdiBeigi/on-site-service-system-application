package ir.example.finalPart03.controller;

import ir.example.finalPart03.dto.bankAccountDto.BankAccountRequestDto;
import ir.example.finalPart03.dto.bankAccountDto.BankAccountResponseDto;
import ir.example.finalPart03.dto.bankAccountDto.BankAccountSavingDto;
import ir.example.finalPart03.dto.commentDto.CommentRequestDto;
import ir.example.finalPart03.dto.commentDto.CommentsResponseDto;
import ir.example.finalPart03.dto.orderDto.OrderRequestDto;
import ir.example.finalPart03.dto.orderDto.OrderResponseDto;
import ir.example.finalPart03.dto.subServiceDto.SubServiceResponseDto;
import ir.example.finalPart03.dto.suggestionDto.SuggestionResponseDto;
import ir.example.finalPart03.model.*;
import ir.example.finalPart03.service.*;
import ir.example.finalPart03.service.impl.CaptchaService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/customer")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    private final ModelMapper modelMapper;

    private final OrderService orderService;

    private final ServicesService servicesService;

    private final SubServiceService subServiceService;

    private final CommentService commentService;

    private final BankAccountService bankAccountService;

    private final CaptchaService captchaService;

    private final SuggestionService suggestionService;


    @PutMapping("/changePassword/{customerId}/{oldPassword}/{password}/{confirmingPassword}")
    ResponseEntity<Void> changePassword(@PathVariable Long customerId, @PathVariable String oldPassword, @PathVariable String password, @PathVariable String confirmingPassword) {
        customerService.changePassword(customerId, oldPassword, password, confirmingPassword);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/saveOrder/{subServiceBasePrice}")
    public ResponseEntity<OrderResponseDto> saveOrder(@RequestBody OrderRequestDto orderRequestDto, @PathVariable Double subServiceBasePrice) {
        Order orderMapper = modelMapper.map(orderRequestDto, Order.class);
        Order order = orderService.saveOrder(orderMapper, subServiceBasePrice, orderRequestDto.getSubServicesId(), orderRequestDto.getCustomerId());
        OrderResponseDto orderResponseDto = modelMapper.map(order, OrderResponseDto.class);
        return new ResponseEntity<>(orderResponseDto, HttpStatus.CREATED);
    }

    @GetMapping("/findAllServices")
    public ResponseEntity<List<Services>> findAllServices() {
        return new ResponseEntity<>(servicesService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/findAllSubServices")
    public ResponseEntity<List<SubServices>> findAllSubServices() {
        return new ResponseEntity<>(subServiceService.findAllSubService(), HttpStatus.OK);
    }

    @PostMapping("/saveComment")
    ResponseEntity<CommentsResponseDto> saveComments(@RequestBody CommentRequestDto commentRequestDto) {
        Comments comments = modelMapper.map(commentRequestDto, Comments.class);
        Comments savedComment = commentService.saveComment(comments, commentRequestDto.getOrderId());
        CommentsResponseDto commentsResponseDto = modelMapper.map(savedComment, CommentsResponseDto.class);
        return new ResponseEntity<>(commentsResponseDto, HttpStatus.CREATED);
    }

    @GetMapping("/findAllByServiceId/{serviceId}")
    public ResponseEntity<List<SubServiceResponseDto>> findAllByServiceId(@PathVariable Long serviceId) {
        List<SubServices> subServiceServiceAllByServiceId = subServiceService.findAllByServiceId(serviceId);
        List<SubServiceResponseDto> subServiceResponseDtoList = new ArrayList<>();
        for (SubServices subServices : subServiceServiceAllByServiceId
        ) {
            SubServiceResponseDto subServiceResponseDto = modelMapper.map(subServices, SubServiceResponseDto.class);
            subServiceResponseDtoList.add(subServiceResponseDto);
        }
        return ResponseEntity.ok(subServiceResponseDtoList);
    }

    @GetMapping("/findAllByCustomerIdOrOrderBySuggestedPrice/{customerId}")
    public ResponseEntity<List<SuggestionResponseDto>> findAllByCustomerIdOrOrderBySuggestedPrice(@PathVariable Long customerId) {
        List<Suggestions> allByCustomerIdOrOrderBySuggestedPrice = suggestionService.findAllByCustomerIdOrOrderBySuggestedPrice(customerId);
        List<SuggestionResponseDto> suggestionResponseDtoList = new ArrayList<>();
        for (Suggestions suggestion :
                allByCustomerIdOrOrderBySuggestedPrice) {
            SuggestionResponseDto suggestionResponseDto = modelMapper.map(suggestion, SuggestionResponseDto.class);
            suggestionResponseDtoList.add(suggestionResponseDto);
        }
        return new ResponseEntity<>(suggestionResponseDtoList, HttpStatus.OK);
    }

    @GetMapping("/findAllByCustomerIdOrOrderByTotalScores/{customerId}")
    public ResponseEntity<List<SuggestionResponseDto>> findAllByCustomerIdOrOrderByTotalScores(@PathVariable Long customerId) {
        List<Suggestions> allByCustomerIdOrOrderByTotalScores = suggestionService.findAllByCustomerIdOrOrderByTotalScores(customerId);
        List<SuggestionResponseDto> suggestionResponseDtoList = new ArrayList<>();
        for (Suggestions suggestion :
                allByCustomerIdOrOrderByTotalScores) {
            SuggestionResponseDto suggestionResponseDto = modelMapper.map(suggestion, SuggestionResponseDto.class);
            suggestionResponseDtoList.add(suggestionResponseDto);
        }
        return ResponseEntity.ok(suggestionResponseDtoList);
    }

    @PutMapping("/changeOrderStatusToComingToYourPlace/{orderId}")
    public ResponseEntity<OrderResponseDto> changeOrderStatusToComingToYourPlace(@PathVariable Long orderId) {
        Order order = orderService.changeOrderStatusToComingToYourPlace(orderId);
        OrderResponseDto orderResponseDto = modelMapper.map(order, OrderResponseDto.class);
        return ResponseEntity.ok(orderResponseDto);
    }

    @PutMapping("/changeOrderStatusToStarted/{orderId}/{customerId}")
    public ResponseEntity<OrderResponseDto> changeOrderStatusToStartedByCustomer(@PathVariable Long orderId, @PathVariable Long customerId) {
        Order order = orderService.changeOrderStatusToStartedByCustomer(orderId, customerId);
        OrderResponseDto orderResponseDto = modelMapper.map(order, OrderResponseDto.class);
        return ResponseEntity.ok(orderResponseDto);
    }

    @PutMapping("/changeOrderStatusToDone/{orderId}/{customerId}")
    public ResponseEntity<OrderResponseDto> changeOrderStatusToDone(@PathVariable Long orderId, @PathVariable Long customerId) {
        Order order = orderService.changeOrderStatusToDone(orderId, customerId);
        OrderResponseDto orderResponseDto = modelMapper.map(order, OrderResponseDto.class);
        return ResponseEntity.ok(orderResponseDto);
    }

    @PutMapping("/changeOrderStatusToPaid/{orderId}")
    public ResponseEntity<OrderResponseDto> changeOrderStatusToPaid(@PathVariable Long orderId) {
        Order order = orderService.changeOrderStatusToPaid(orderId);
        OrderResponseDto orderResponseDto = modelMapper.map(order, OrderResponseDto.class);
        return ResponseEntity.ok(orderResponseDto);
    }

    @PostMapping("/checkOrdersAndUpdateScores/{suggestionId}/{specialistId}")
    public ResponseEntity<Void> checkOrdersAndUpdateScores(@PathVariable Long suggestionId, @PathVariable Long specialistId) {
        orderService.checkOrdersAndUpdateScores(suggestionId, specialistId);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/credit/{customerId}/{specialistId}/{paymentAmount}/{orderId}")
    public ResponseEntity<Void> orderPaymentByCustomerFromCredit(@PathVariable Long customerId, @PathVariable Long specialistId, @PathVariable Double paymentAmount, @PathVariable Long orderId) {
        bankAccountService.finalPaymentByCustomerFromCredit(customerId, specialistId, paymentAmount, orderId);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/online/finalPaymentFromOnlineGateway")
    public void finalPaymentFromOnlineGateway(@RequestBody BankAccountRequestDto bankAccountRequestDto) {
        BankAccount bankAccount = BankAccountRequestDto.dtoToBankAccount(bankAccountRequestDto);
        bankAccountService.finalPaymentByCustomerFromOnlinePaymentGateway(bankAccount, bankAccountRequestDto.getSpecialistId(), bankAccountRequestDto.getPrice(), bankAccountRequestDto.getOrderId());

    }

    @GetMapping("/captcha")
    public ResponseEntity<String> getCaptcha(HttpSession session) {
        String captcha = captchaService.generateCaptchaToken(session);
        return ResponseEntity.ok(captcha);
    }

    @PostMapping("/verify-captcha")
    public ResponseEntity<String> verifyCaptcha(@RequestBody Map<String, String> body, HttpSession session) {
        boolean valid = captchaService.validateCaptcha(body.get("captchaAnswer"), session);
        return valid ? ResponseEntity.ok("Captcha is valid") : ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid captcha");
    }

    @GetMapping("/findAllOrdersByCustomerIdAndOrderStatus/{customerId}/{orderStatus}")
    public ResponseEntity<List<OrderResponseDto>> findAllOrdersByCustomerIdAndOrderStatus(@PathVariable Long customerId, @PathVariable String orderStatus) {
        List<OrderResponseDto> orderResponseDtoList = new ArrayList<>();
        List<Order> allOrdersByCustomerIdAndOrderStatus = orderService.findAllOrdersByCustomerIdAndOrderStatus(customerId, orderStatus);
        for (Order order : allOrdersByCustomerIdAndOrderStatus) {
            OrderResponseDto orderResponseDto = modelMapper.map(order, OrderResponseDto.class);
            orderResponseDtoList.add(orderResponseDto);
        }
        return new ResponseEntity<>(orderResponseDtoList, HttpStatus.OK);
    }

    @GetMapping("/findBankAccount/{customerId}")
    public ResponseEntity<BankAccountResponseDto> findBankAccountByCustomerId(@PathVariable Long customerId) {
        BankAccount bankAccByCustomerId = bankAccountService.findBankAccByCustomerId(customerId);
        BankAccountResponseDto bankAccountResponseDto = modelMapper.map(bankAccByCustomerId, BankAccountResponseDto.class);
        return new ResponseEntity<>(bankAccountResponseDto, HttpStatus.OK);
    }

    @PostMapping("/bankAccount/save")
    public ResponseEntity<BankAccountResponseDto> saveBankAccount(@Valid @RequestBody BankAccountSavingDto bankAccountSavingDto) {
        BankAccount bankAccount = BankAccountSavingDto.dtoToBankAccount(bankAccountSavingDto);
        BankAccount saveBankAccount = bankAccountService.saveBankAccount(bankAccount, bankAccountSavingDto.getSpecialistId(), bankAccountSavingDto.getCustomerId());
        BankAccountResponseDto bankAccountResponseDto = modelMapper.map(saveBankAccount, BankAccountResponseDto.class);
        return new ResponseEntity<>(bankAccountResponseDto, HttpStatus.CREATED);
    }
}
