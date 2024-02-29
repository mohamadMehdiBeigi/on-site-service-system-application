package ir.example.finalPart03.controller;

import ir.example.finalPart03.dto.bankAccountDto.BankAccountResponseDto;
import ir.example.finalPart03.dto.bankAccountDto.BankAccountSavingDto;
import ir.example.finalPart03.dto.commentDto.CommentsResponseDto;
import ir.example.finalPart03.dto.orderDto.OrderResponseDto;
import ir.example.finalPart03.dto.specialistDto.SpecialistResponseDto;
import ir.example.finalPart03.dto.suggestionDto.SuggestionRequestDto;
import ir.example.finalPart03.dto.suggestionDto.SuggestionResponseDto;
import ir.example.finalPart03.model.*;
import ir.example.finalPart03.service.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/specialist")
@RequiredArgsConstructor
public class SpecialistController {

    private final SpecialistService specialistService;

    private final ModelMapper modelMapper;

    private final SuggestionService suggestionService;

    private final OrderService orderService;

    private final BankAccountService bankAccountService;

    private final CommentService commentService;

    @PostMapping("/saveSuggestion")
    public ResponseEntity<SuggestionResponseDto> saveSuggestion(@RequestBody SuggestionRequestDto suggestionRequestDto) {
        Suggestions suggestions = SuggestionRequestDto.dtoToSuggestion(suggestionRequestDto);
        Suggestions saveSuggestion = suggestionService.saveSuggestion(suggestions, suggestionRequestDto.getOrderId(), suggestionRequestDto.getSpecialistId());
        SuggestionResponseDto suggestionResponseDto = SuggestionResponseDto.suggestionToResponseDto(saveSuggestion);
        return new ResponseEntity<>(suggestionResponseDto, HttpStatus.CREATED);
    }


    @PostMapping("/saveImageToFile/{specialistId}")
    public ResponseEntity<String> saveImageToFile(@PathVariable Long specialistId) {
        specialistService.saveImageToFile(specialistId);
        return ResponseEntity.ok("successfully added");
    }

    @PutMapping("/changePassword/{specialistId}/{oldPassword}/{password}/{confirmingPassword}")
    public ResponseEntity<SpecialistResponseDto> changePassword(@PathVariable Long specialistId, @PathVariable String oldPassword, @PathVariable String password, @PathVariable String confirmingPassword) {
        Specialist specialist = specialistService.changePassword(specialistId, oldPassword, password, confirmingPassword);
        SpecialistResponseDto specialistResponseDto = modelMapper.map(specialist, SpecialistResponseDto.class);
        return ResponseEntity.ok(specialistResponseDto);
    }

    @PutMapping("/addSubServiceToSpecialist/{specialistId}/{subServiceId}")
    public ResponseEntity<Void> addSubServiceToSpecialist(@PathVariable Long specialistId, @PathVariable Long subServiceId) {
        specialistService.addSubServiceToSpecialist(specialistId, subServiceId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/seeAverageScore/{specialistId}")
    public ResponseEntity<SpecialistResponseDto> seeAverageScore(@PathVariable Long specialistId) {
        Specialist specialist = specialistService.seeAverageScore(specialistId);
        SpecialistResponseDto specialistResponseDto = modelMapper.map(specialist, SpecialistResponseDto.class);
        return new ResponseEntity<>(specialistResponseDto, HttpStatus.OK);
    }



    @GetMapping("/seeScoreBySpecialistId/{specialistId}")
    public ResponseEntity<List<Double>> findAllScoresBySpecialistId(@PathVariable Long specialistId) {
        List<Comments> allBySpecialistId = commentService.findAllBySpecialistId(specialistId);

        List<CommentsResponseDto> commentsResponseDtoList = new ArrayList<>();

        for (Comments c : allBySpecialistId) {
            CommentsResponseDto commentsResponseDto = modelMapper.map(c, CommentsResponseDto.class);
            commentsResponseDtoList.add(commentsResponseDto);
        }
        List<Double> collect = commentsResponseDtoList.stream().map(CommentsResponseDto::getScore).toList();
        return ResponseEntity.accepted().body(collect);
    }


    @GetMapping("/findOrder/{orderId}")
    public ResponseEntity<OrderResponseDto> findById(@PathVariable Long orderId) {
        Order order = orderService.findById(orderId);
        OrderResponseDto orderResponseDto = modelMapper.map(order, OrderResponseDto.class);
        return ResponseEntity.ok(orderResponseDto);
    }

    @GetMapping("/findAvailableOrdersForSpecialist/{specialistId}")
    public ResponseEntity<List<OrderResponseDto>> findAvailableOrdersForSpecialist(@PathVariable Long specialistId) {
        List<Order> availableOrdersForSpecialist = orderService.findAvailableOrdersForSpecialist(specialistId);
        List<OrderResponseDto> orderResponseDtoList = new ArrayList<>();
        for (Order order : availableOrdersForSpecialist) {
            OrderResponseDto orderResponseDto = modelMapper.map(order, OrderResponseDto.class);
            orderResponseDtoList.add(orderResponseDto);
        }
        return ResponseEntity.ok(orderResponseDtoList);
    }

    @GetMapping("/findAllByOrderStatusAndSpecialistId/{specialistId}")
    public ResponseEntity<List<OrderResponseDto>> findAllByOrderStatusAndSpecialistId(@PathVariable Long specialistId) {
        List<Order> allByOrderStatusAndSpecialistId = orderService.findAllByOrderStatusAndSpecialistId(specialistId);
        List<OrderResponseDto> orderResponseDtosList = new ArrayList<>();
        for (Order order : allByOrderStatusAndSpecialistId) {
            OrderResponseDto orderResponseDto = modelMapper.map(order, OrderResponseDto.class);
            orderResponseDtosList.add(orderResponseDto);
        }
        return ResponseEntity.ok(orderResponseDtosList);
    }

    @GetMapping("/findAllOrderBySpecialistIdAndOrderStatus/{specialistId}/{orderStatus}")
    public ResponseEntity<List<OrderResponseDto>> findAllOrderBySpecialistIdAndOrderStatus(@PathVariable Long specialistId, @PathVariable String orderStatus) {
        List<OrderResponseDto> orderResponseDtoList = new ArrayList<>();
        List<Order> orderList = orderService.findAllOrderBySpecialistIdAndOrderStatus(specialistId, orderStatus);
        for (Order order : orderList) {
            OrderResponseDto orderResponseDto = modelMapper.map(order, OrderResponseDto.class);
            orderResponseDtoList.add(orderResponseDto);
        }
        return new ResponseEntity<>(orderResponseDtoList, HttpStatus.OK);
    }

    @GetMapping("/findBankAccount/{specialistId}")
    public ResponseEntity<BankAccountResponseDto> findBankAccountByCustomerId(@PathVariable Long specialistId) {
        BankAccount bankAccBySpecialistId = bankAccountService.findBankAccBySpecialistId(specialistId);
        BankAccountResponseDto bankAccountResponseDto = modelMapper.map(bankAccBySpecialistId, BankAccountResponseDto.class);
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
