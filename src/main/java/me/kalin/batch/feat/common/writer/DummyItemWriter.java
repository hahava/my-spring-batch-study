package me.kalin.batch.feat.common.writer;

import org.springframework.batch.item.ItemWriter;

import java.util.List;

public class DummyItemWriter implements ItemWriter {
    @Override
    public void write(List list) throws Exception {
    }
}
