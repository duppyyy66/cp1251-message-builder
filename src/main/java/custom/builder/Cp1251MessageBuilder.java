package custom.builder;

import com.ibm.eventstreams.connect.mqsink.builders.MessageBuilder;
import org.apache.kafka.connect.sink.SinkRecord;

import javax.jms.BytesMessage;
import javax.jms.JMSContext;
import javax.jms.Message;
import java.nio.charset.Charset;
import java.util.Map;

public class Cp1251MessageBuilder implements MessageBuilder {

    private Charset charset = Charset.forName("Windows-1251");

    @Override
    public Message fromSinkRecord(JMSContext context, SinkRecord record) {
        Object value = record.value();

        if (value == null) {
            return null;
        }

        byte[] encodedMessage;

        if (value instanceof byte[]) {
            encodedMessage = (byte[]) value;
        } else {
            String stringValue = value.toString();
            encodedMessage = stringValue.getBytes(charset);
        }

        try {
            BytesMessage message = context.createBytesMessage();
            message.writeBytes(encodedMessage);
            return message;
        } catch (Exception e) {
            throw new RuntimeException("Failed to create JMS message", e);
        }
    }

    @Override
    public void configure(Map<String, String> props) {
        String charsetName = props.get("mq.message.encoding");
        if (charsetName != null && !charsetName.isBlank()) {
            this.charset = Charset.forName(charsetName);
        }
    }
}
