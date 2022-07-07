package com.java.nie.common.constants;

/**
 * 审批流程状态常量
 */
public interface CompletionApproveStatusConstants {
    //审核状态：0草稿；1待审核；2资料审核通过；3资料审核不通过；4现场验收通过；5现场验收不通过；6综合审核通过；7综合审核不通过
    Integer NO_COMMIT=0;
    /**
     * 待审核
     */
    Integer TO_REVIEW=1;
    /**
     * 资料审核通过
     */
    Integer DATA_REVIEW_YES=2;
    /**
     * 资料审核不通过
     */
    Integer DATA_REVIEW_NO=3;
    /**、
     * 现场验收通过
     */
    Integer ACCEPTANCE_REVIEW_YES=4;
    /**
     * 现场验收驳回
     */
    Integer ACCEPTANCE_REVIEW_NO=5;
    /**
     * 综合审核通过--也就是归档
     */
    Integer SYNTHESIS_REVIEW_YES=6;
    /**
     * 综合审核驳回
     */
    Integer SYNTHESIS_REVIEW_NO=7;
    /**
     * 归档
     */
    Integer GUI_DANG=8;
}
