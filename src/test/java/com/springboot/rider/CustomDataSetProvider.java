package com.springboot.rider;

import com.github.database.rider.core.api.dataset.DataSetProvider;
import com.github.database.rider.core.dataset.builder.DataSetBuilder;
import org.dbunit.dataset.DataSetException;
import org.dbunit.dataset.IDataSet;
import org.springframework.stereotype.Component;

//@Component
public class CustomDataSetProvider implements DataSetProvider {
    @Override
    public IDataSet provide() throws DataSetException {
        DataSetBuilder builder = new DataSetBuilder();
        IDataSet dataSet = builder
                .table("PRODUCT")
                .row()
                .column("PRODUCTID", 1)
                .column("TITLE", "面食")
                .column("NAME", "馒头11")
                .column("TYPE", 1)
                .row()
                .column("PRODUCTID", 2)
                .column("TITLE", "电子设备")
                .column("NAME", "苹果手机111")
                .column("TYPE", 2).build();
        return dataSet;
    }
}
