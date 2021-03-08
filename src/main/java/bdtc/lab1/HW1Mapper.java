package bdtc.lab1;

import eu.bitwalker.useragentutils.Browser;
import eu.bitwalker.useragentutils.UserAgent;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;
import java.util.Arrays;
import java.util.regex.Pattern;


public class HW1Mapper extends Mapper<LongWritable, Text, Text, IntWritable> {

    private final static IntWritable one = new IntWritable(1);
    private Text word = new Text();

    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        String line = value.toString();
        if (Pattern.matches("^20[0-9]{2}-(0[1-9]|1[0-2])-(0[1-9]|1[0-9]|2[0-9]|3[0-1]) (0[0-9]|1[0-9]|2[0-3])(:(0[0-9]|[1-5][0-9])){2},[0-7]$",line)) {
            String[] ml = line.split(":|,");
            if (ml[0].isEmpty() || ml[ml.length - 1].isEmpty()) {
                context.getCounter(CounterType.MALFORMED).increment(1);
            } else {
                word.set(ml[0]);
                one.set(Integer.parseInt(ml[ml.length - 1]));
                context.write(word, one);
            }
        }
        else {
            context.getCounter(CounterType.MALFORMED).increment(1);
        }
    }
}
