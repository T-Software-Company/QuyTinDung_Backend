package com.tsoftware.qtd.constants.EnumType;

import com.tsoftware.qtd.handler.ApprovalSubmittedHandler;
import com.tsoftware.qtd.handler.ApprovedHandler;
import com.tsoftware.qtd.handler.NotificationHandler;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum NotificationType {
  // Submit notifications
  CREATE_LOAN_REQUEST(
      ApprovalSubmittedHandler.class,
      "Hồ sơ vay mới",
      "Hồ sơ vay được tạo và chờ xét duyệt",
      "Bạn có hồ sơ vay cần xét duyệt"),
  CREATE_LOAN_PLAN(
      ApprovalSubmittedHandler.class,
      "Kế hoạch vay mới",
      "Kế hoạch vay được tạo và chờ xét duyệt",
      "Bạn có kế hoạch vay cần xét duyệt"),
  CREATE_FINANCIAL_INFO(
      ApprovalSubmittedHandler.class,
      "Hồ sơ tài chính",
      "Thông tin tài chính được cập nhật và chờ xét duyệt",
      "Bạn có hồ sơ tài chính cần xét duyệt"),
  CREATE_ASSETS(
      ApprovalSubmittedHandler.class,
      "Thông tin tài sản đảm bảo",
      "Hồ sơ tài sản đảm bảo được cập nhật và chờ xét duyệt",
      "Bạn có hồ sơ tài sản cần xét duyệt"),
  CREATE_VALUATION_MEETING(
      ApprovalSubmittedHandler.class,
      "Lịch thẩm định tài sản",
      "Cuộc họp thẩm định tài sản được lên lịch",
      "Bạn có lịch họp thẩm định tài sản"),
  CREATE_VALUATION_REPORT(
      ApprovalSubmittedHandler.class,
      "Báo cáo thẩm định",
      "Báo cáo thẩm định được hoàn thành và chờ xét duyệt",
      "Bạn có báo cáo thẩm định cần xét duyệt"),
  CREATE_CREDIT_RATING(
      ApprovalSubmittedHandler.class,
      "Đánh giá tín dụng",
      "Đánh giá tín dụng được đã được tạo",
      null,
      "Đánh giá tín dụng của bạn đã được tạo"),
  CREATE_APPRAISAL_MEETING(
      ApprovalSubmittedHandler.class,
      "Lịc thẩm định khoản vay",
      "Cuộc họp thẩm định khoản vay được lên lịch",
      "Bạn có lịch họp thẩm định khoản vay"),
  CREATE_APPRAISAL_REPORT(
      ApprovalSubmittedHandler.class,
      "Báo cáo thẩm định khoản vay",
      "Báo cáo thẩm định khoản vay được tạo và chờ xét duyệt",
      "Bạn có báo cáo thẩm định khoản vay cần xét duyệt"),

  UPDATE_LOAN_REQUEST(
      ApprovalSubmittedHandler.class,
      "Cập nhật hồ sơ vay",
      "Hồ sơ vay đã được cập nhật và đang chờ xét duyệt",
      "Bạn có một hồ sơ vay cần xét duyệt"),
  UPDATE_LOAN_PLAN(
      ApprovalSubmittedHandler.class,
      "Cập nhật kế hoạch vay",
      "Kế hoạch vay đã được cập nhật và đang chờ xét duyệt",
      "Bạn có một kế hoạch vay cần xét duyệt"),
  UPDATE_FINANCIAL_INFO(
      ApprovalSubmittedHandler.class,
      "Cập nhật thông tin tài chính",
      "Thông tin tài chính đã được cập nhật và đang chờ xét duyệt",
      "Bạn có một hồ sơ tài chính cần xét duyệt"),
  UPDATE_ASSETS(
      ApprovalSubmittedHandler.class,
      "Cập nhật tài sản đảm bảo",
      "Hồ sơ tài sản đảm bảo đã được cập nhật và đang chờ xét duyệt",
      "Bạn có một hồ sơ tài sản cần xét duyệt"),
  UPDATE_VALUATION_REPORT(
      ApprovalSubmittedHandler.class,
      "Cập nhật báo cáo thẩm định",
      "Báo cáo thẩm định đã được cập nhật và đang chờ xét duyệt",
      "Bạn có một báo cáo thẩm định cần xét duyệt"),
  UPDATE_APPRAISAL_REPORT(
      ApprovalSubmittedHandler.class,
      "Cập nhật báo cáo thẩm định khoản vay",
      "Báo cáo thẩm định khoản vay đã được cập nhật và đang chờ xét duyệt",
      "Bạn có một báo cáo thẩm định khoản vay cần xét duyệt"),

  // Approval notifications
  APPROVE_LOAN_REQUEST(
      ApprovedHandler.class,
      "Kết quả xét duyệt hồ sơ vay",
      "Hồ sơ vay đã được phê duyệt",
      "Bạn có kết quả phê duyệt hồ sơ vay",
      "Hồ sơ vay của bạn đã được chấp thuận"),
  APPROVE_LOAN_PLAN(
      ApprovedHandler.class,
      "Kết quả xét duyệt kế hoạch vay",
      "Kế hoạch vay đã được phê duyệt",
      "Bạn có kết quả phê duyệt kế hoạch vay",
      "Kế hoạch vay của bạn đã được chấp thuận"),
  APPROVE_FINANCIAL_INFO(
      ApprovedHandler.class,
      "Kết quả xét duyệt hồ sơ tài chính",
      "Thông tin tài chính đã được phê duyệt",
      "Bạn có kết quả phê duyệt hồ sơ tài chính",
      "Hồ sơ tài chính của bạn đã được chấp thuận"),
  APPROVE_ASSETS(
      ApprovedHandler.class,
      "Kết quả xét duyệt tài sản đảm bảo",
      "Hồ sơ tài sản đảm bảo đã được phê duyệt",
      "Bạn có kết quả phê duyệt tài sản đảm bảo",
      "Tài sản đảm bảo của bạn đã được chấp thuận"),
  APPROVE_VALUATION_REPORT(
      ApprovedHandler.class,
      "Kết quả xét duyệt báo cáo thẩm định",
      "Báo cáo thẩm định đã được phê duyệt",
      "Bạn có kết quả phê duyệt báo cáo thẩm định",
      "Tài sản đảm bảo của bạn đã được định giá"),
  APPROVE_APPRAISAL_REPORT(
      ApprovedHandler.class,
      "Kết quả xét duyệt báo cáo thẩm định khoản vay",
      "Báo cáo thẩm định khoản vay đã được phê duyệt",
      "Bạn có kết quả phê duyệt báo cáo thẩm định khoản vay",
      "Báo cáo thẩm định khoản vay của bạn đã được chấp thuận"),

  // Rejection notifications
  REJECT_LOAN_REQUEST(
      ApprovedHandler.class,
      "Kết quả xét duyệt hồ sơ vay",
      "Hồ sơ vay đã bị từ chối",
      "Bạn có kết quả phê duyệt hồ sơ vay",
      "Hồ sơ vay của bạn chưa được chấp thuận"),
  REJECT_LOAN_PLAN(
      ApprovedHandler.class,
      "Kết quả xét duyệt kế hoạch vay",
      "Kế hoạch vay đã bị từ chối",
      "Bạn có kết quả phê duyệt kế hoạch vay",
      "Kế hoạch vay của bạn chưa được chấp thuận"),
  REJECT_FINANCIAL_INFO(
      ApprovedHandler.class,
      "Kết quả xét duyệt hồ sơ tài chính",
      "Thông tin tài chính đã bị từ chối",
      "Bạn có kết quả phê duyệt hồ sơ tài chính",
      "Hồ sơ tài chính của bạn chưa được chấp thuận"),
  REJECT_ASSETS(
      ApprovedHandler.class,
      "Kết quả xét duyệt tài sản đảm bảo",
      "Hồ sơ tài sản đảm bảo đã bị từ chối",
      "Bạn có kết quả phê duyệt tài sản đảm bảo",
      "Tài sản đảm bảo của bạn chưa được chấp thuận"),
  REJECT_VALUATION_REPORT(
      ApprovedHandler.class,
      "Kết quả xét duyệt báo cáo thẩm định",
      "Báo cáo thẩm định đã bị từ chối",
      "Bạn có kết quả phê duyệt báo cáo thẩm định",
      "Định giá tài sản của bạn chưa được chấp thuận"),
  REJECT_APPRAISAL_REPORT(
      ApprovedHandler.class,
      "Kết quả xét duyệt báo cáo thẩm định khoản vay",
      "Báo cáo thẩm định khoản vay đã bị từ chối",
      "Bạn có kết quả phê duyệt báo cáo thẩm định khoản vay",
      "Báo cáo thẩm định khoản vay của bạn chưa được chấp thuận"),
  CANCELLED_APPLICATION(
      ApprovedHandler.class,
      "Hồ sơ vay bị huỷ",
      "Hồ sơ vay đã bị huỷ bỏ",
      "Một hồ sơ vay đã bị huỷ bỏ",
      "Hồ sơ vay của bạn đã bị huỷ bỏ");
  final Class<? extends NotificationHandler> handler;
  final String title;
  final String content;
  final String employeeMessage;
  final String customerMessage;

  NotificationType(
      Class<? extends NotificationHandler> handler,
      String title,
      String content,
      String employeeMessage) {
    this(handler, title, content, employeeMessage, null);
  }

  public static NotificationType approvedTypeFromProcessType(ProcessType processType) {
    return switch (processType) {
      case CREATE_LOAN_REQUEST -> APPROVE_LOAN_REQUEST;
      case CREATE_LOAN_PLAN -> APPROVE_LOAN_PLAN;
      case CREATE_FINANCIAL_INFO -> APPROVE_FINANCIAL_INFO;
      case CREATE_ASSETS -> APPROVE_ASSETS;
      case CREATE_VALUATION_REPORT -> APPROVE_VALUATION_REPORT;
      case CREATE_APPRAISAL_REPORT -> APPROVE_APPRAISAL_REPORT;
    };
  }

  public static NotificationType submittedTypeFromProcessType(ProcessType processType) {
    return switch (processType) {
      case CREATE_LOAN_REQUEST -> CREATE_LOAN_REQUEST;
      case CREATE_LOAN_PLAN -> CREATE_LOAN_PLAN;
      case CREATE_FINANCIAL_INFO -> CREATE_FINANCIAL_INFO;
      case CREATE_ASSETS -> CREATE_ASSETS;
      case CREATE_VALUATION_REPORT -> CREATE_VALUATION_REPORT;
      case CREATE_APPRAISAL_REPORT -> CREATE_APPRAISAL_REPORT;
    };
  }

  public static NotificationType updatedTypeFromProcessType(ProcessType processType) {
    return switch (processType) {
      case CREATE_LOAN_REQUEST -> UPDATE_LOAN_REQUEST;
      case CREATE_LOAN_PLAN -> UPDATE_LOAN_PLAN;
      case CREATE_FINANCIAL_INFO -> UPDATE_FINANCIAL_INFO;
      case CREATE_ASSETS -> UPDATE_ASSETS;
      case CREATE_VALUATION_REPORT -> UPDATE_VALUATION_REPORT;
      case CREATE_APPRAISAL_REPORT -> UPDATE_APPRAISAL_REPORT;
    };
  }

  public static NotificationType rejectedTypeFromProcessType(ProcessType processType) {
    return switch (processType) {
      case CREATE_LOAN_REQUEST -> REJECT_LOAN_REQUEST;
      case CREATE_LOAN_PLAN -> REJECT_LOAN_PLAN;
      case CREATE_FINANCIAL_INFO -> REJECT_FINANCIAL_INFO;
      case CREATE_ASSETS -> REJECT_ASSETS;
      case CREATE_VALUATION_REPORT -> REJECT_VALUATION_REPORT;
      case CREATE_APPRAISAL_REPORT -> REJECT_APPRAISAL_REPORT;
    };
  }
}
