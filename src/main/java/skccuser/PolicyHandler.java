package skccuser;

import skccuser.config.kafka.KafkaProcessor;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Service
public class PolicyHandler{
    @StreamListener(KafkaProcessor.INPUT)
    public void onStringEventListener(@Payload String eventString){

    }


    @Autowired
    CouponRepository couponRepository;

    @StreamListener(KafkaProcessor.INPUT)
    public void wheneverShipped_CouponCancel(@Payload Shipped shipped){

        if(shipped.isMe()){
            Coupon coupon = new Coupon();
            coupon.setOrderId(shipped.getOrderId());
            coupon.setPaymentId(shipped.getPaymentId());
            coupon.setDeliveryId(shipped.getId());
            coupon.setStatus("offered");

            couponRepository.save(coupon) ;
        }
    }

}
