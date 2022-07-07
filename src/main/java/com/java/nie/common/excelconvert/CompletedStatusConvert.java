package com.java.nie.common.excelconvert;

import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.converters.ReadConverterContext;
import com.alibaba.excel.converters.WriteConverterContext;
import com.alibaba.excel.enums.CellDataTypeEnum;
import com.alibaba.excel.metadata.data.WriteCellData;
import com.java.nie.common.constants.CompletionApproveStatusConstants;


public class CompletedStatusConvert implements Converter<Integer>{
    @Override
    public Class<?> supportJavaTypeKey() {
        return String.class;
    }

    @Override
    public CellDataTypeEnum supportExcelTypeKey() {
        return CellDataTypeEnum.STRING;
    }

    /**
     * 这里是读的时候会调用 不用管
     *
     * @return
     */
    @Override
    public Integer convertToJavaData(ReadConverterContext<?> context) {
        String stringValue = context.getReadCellData().getStringValue();
        return Integer.valueOf(stringValue);
    }

    /**
     * 这里是写的时候会调用 不用管
     *
     * @return
     */
    @Override
    public WriteCellData<?> convertToExcelData(WriteConverterContext<Integer> context) {
        Integer value = context.getValue() ;
        if (CompletionApproveStatusConstants.NO_COMMIT.equals(value )){
            return new WriteCellData<>("草稿");
        }else if (CompletionApproveStatusConstants.TO_REVIEW.equals(value)){
            return new WriteCellData<>("待审核");
        }else if (CompletionApproveStatusConstants.DATA_REVIEW_YES.equals(value)){
            return new WriteCellData<>("资料审核通过");
        }else if (CompletionApproveStatusConstants.DATA_REVIEW_NO.equals(value)){
            return new WriteCellData<>("资料审核不通过");
        }else if (CompletionApproveStatusConstants.ACCEPTANCE_REVIEW_YES.equals(value)){
            return new WriteCellData<>("现场验收通过");
        }else if (CompletionApproveStatusConstants.ACCEPTANCE_REVIEW_NO.equals(value)){
            return new WriteCellData<>("现场验收驳回");
        }else if (CompletionApproveStatusConstants.SYNTHESIS_REVIEW_YES.equals(value)){
            return new WriteCellData<>("综合审核通过");
        }else if (CompletionApproveStatusConstants.SYNTHESIS_REVIEW_NO.equals(value)){
            return new WriteCellData<>("综合审核驳回");
        }else if (CompletionApproveStatusConstants.GUI_DANG.equals(value)){
            return new WriteCellData<>("归档");
        }else {
            return new WriteCellData<>();
        }

    }


}
