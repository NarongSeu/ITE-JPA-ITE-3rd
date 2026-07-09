package co.istad.ite.payment.init;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CustomerSegmentInitialize {

    private CustomerSegmentInitialize customerSegmentInitialize;
    @PostConstruct
    public void init(){
//        if(customerSegmentInitialize.count == null){
//
//        }
    }
}
