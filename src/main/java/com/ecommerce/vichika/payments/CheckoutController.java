package com.ecommerce.vichika.payments;

import com.ecommerce.vichika.service.CheckoutService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/checkout")
@RequiredArgsConstructor
public class CheckoutController {

    private final CheckoutService checkoutService;

    @PostMapping
    public CheckoutResponse checkout(
            @Valid @RequestBody CheckoutRequest checkoutRequest) {
        return checkoutService.checkout(checkoutRequest);
    }

    @PostMapping("/webhook")
    public void handleWebhook(
            @RequestHeader Map<String,String> headers,
            @RequestBody String payload
    ){
        checkoutService.handleWebhookEvent(new WebhookRequest(headers, payload));
    }

}
