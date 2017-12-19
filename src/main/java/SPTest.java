import com.cirruslink.sparkplug.message.SparkplugBPayloadDecoder;
import com.cirruslink.sparkplug.message.SparkplugBPayloadEncoder;
import com.cirruslink.sparkplug.message.model.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SPTest {

    private static int seq;

    public static void main(String args[]){
        System.out.println("publishing new data");
        // Create the payload and add some metrics
        List<Metric> metrics = new ArrayList<Metric>();
        Metric newMetric = new Metric();
        newMetric.setName("hello");
        newMetric.setValue(12345.23);
        newMetric.setDataType(MetricDataType.Double);
        metrics.add(newMetric);
        try {
            SparkplugBPayload payload = new SparkplugBPayload(
                    new Date(),
                    metrics,
                    getSeqNum(),
                    newUUID(),
                    null);
            SparkplugBPayloadEncoder encoder = new SparkplugBPayloadEncoder();
            byte msg[] = encoder.getBytes(payload);
            System.out.println("Payload encoded");
            SparkplugBPayloadDecoder decoder = new SparkplugBPayloadDecoder();
            SparkplugBPayload rcvPayload = decoder.buildFromByteArray(msg);
            System.out.println(rcvPayload);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void evaluateData(){
        //use jackson to deserialize json into an object that can feed the sparkplug processor
    }

    // Used to add the sequence number
    private static long getSeqNum() throws Exception {
        System.out.println("seq: " + seq);
        if (seq == 256) {
            seq = 0;
        }
        return seq++;
    }

    private static String newUUID() {
        return java.util.UUID.randomUUID().toString();
    }
}
