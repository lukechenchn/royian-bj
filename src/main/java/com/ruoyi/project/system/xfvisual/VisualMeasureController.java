//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.util.StringUtils;
//import org.springframework.validation.annotation.Validated;
//import org.springframework.web.bind.annotation.*;
//
//import javax.validation.ConstraintViolation;
//import javax.validation.ConstraintViolationException;
//import javax.validation.constraints.NotBlank;
//import javax.validation.constraints.Pattern;
//import java.math.BigDecimal;
//import java.time.LocalDateTime;
//import java.util.*;
//import java.util.concurrent.ConcurrentHashMap;
//import java.util.regex.PatternSyntaxException;
//import java.util.stream.Collectors;
//
///**
// * 视觉测量数据接收控制器
// * 处理设备坐标测量数据的接收、校验与处理
// */
//@RestController
//@RequestMapping("/visual")
//@Validated
//public class VisualMeasureController {
//
//    // 坐标数值正则：支持正负整数、小数，最多保留6位小数
//    private static final String COORDINATE_PATTERN = "^[-+]?\\d+(\\.\\d{1,6})?$";
//    // 角度数值正则：支持正负角度值，最多保留6位小数
//    private static final String ANGLE_PATTERN = "^[-+]?\\d+(\\.\\d{1,6})?$";
//    // zbx_id格式正则：字母数字下划线组合，3-32位
//    private static final String ZBX_ID_PATTERN = "^[a-zA-Z0-9_]{3,32}$";
//
//    // 模拟数据存储（实际项目中应使用数据库）
//    private final Map<String, MeasureData> measureDataStorage = new ConcurrentHashMap<>();
//
//    /**
//     * 接收坐标测量数据
//     * POST /visual/measure
//     * 处理包含zbx_id及坐标信息的JSON数组
//     */
//    @PostMapping("/measure")
//    public ResponseEntity<ApiResponse<MeasureResult>> receiveMeasureData(
//            @RequestBody List<@Valid MeasureRequest> measureList) {
//
//        // 初始化处理结果统计
//        int successCount = 0;
//        int failCount = 0;
//        List<String> errorDetails = new ArrayList<>();
//        LocalDateTime processTime = LocalDateTime.now();
//
//        // 批量处理测量数据
//        for (MeasureRequest request : measureList) {
//            try {
//                // 1. 高级数据格式校验（补充注解校验的额外逻辑）
//                validateCoordinatePrecision(request);
//
//                // 2. 业务逻辑处理
//                MeasureData data = convertToMeasureData(request);
//                saveMeasureData(data);
//
//                // 3. 模拟其他业务操作（如发送通知、数据转发等）
//                notifyDataReceived(data);
//
//                successCount++;
//            } catch (Exception e) {
//                failCount++;
//                errorDetails.add(String.format(
//                        "zbx_id=%s 处理失败: %s",
//                        request.getZbxId(),
//                        e.getMessage()
//                ));
//            }
//        }
//
//        // 构建处理结果
//        MeasureResult result = new MeasureResult();
//        result.setTotalCount(measureList.size());
//        result.setSuccessCount(successCount);
//        result.setFailCount(failCount);
//        result.setProcessTime(processTime);
//        result.setErrorDetails(errorDetails);
//
//        // 构建响应
//        ApiResponse<MeasureResult> response = new ApiResponse<>();
//        response.setCode(200);
//        response.setMessage("数据处理完成");
//        response.setData(result);
//
//        return ResponseEntity.ok(response);
//    }
//
//    /**
//     * 验证坐标精度是否符合工业标准（额外校验逻辑）
//     */
//    private void validateCoordinatePrecision(MeasureRequest request) {
//        // 检查X坐标精度
//        checkDecimalPrecision(request.getX(), "x", 6);
//        // 检查Y坐标精度
//        checkDecimalPrecision(request.getY(), "y", 6);
//        // 检查Z坐标精度
//        checkDecimalPrecision(request.getZ(), "z", 6);
//        // 检查RX角度精度
//        checkDecimalPrecision(request.getRx(), "rx", 6);
//        // 检查RY角度精度
//        checkDecimalPrecision(request.getRy(), "ry", 6);
//        // 检查RZ角度精度
//        checkDecimalPrecision(request.getRz(), "rz", 6);
//    }
//
//    /**
//     * 检查小数位数是否超过限制
//     */
//    private void checkDecimalPrecision(String value, String field, int maxScale) {
//        if (!StringUtils.hasText(value)) {
//            return; // 空值已由注解校验处理
//        }
//
//        try {
//            BigDecimal decimal = new BigDecimal(value);
//            if (decimal.scale() > maxScale) {
//                throw new IllegalArgumentException(
//                        String.format("%s字段精度超限，最多支持%d位小数", field, maxScale)
//                );
//            }
//        } catch (NumberFormatException e) {
//            throw new IllegalArgumentException(
//                    String.format("%s字段格式错误，无法转换为数字: %s", field, value)
//            );
//        }
//    }
//
//    /**
//     * 转换请求对象为数据实体
//     */
//    private MeasureData convertToMeasureData(MeasureRequest request) {
//        MeasureData data = new MeasureData();
//        data.setZbxId(request.getZbxId());
//        data.setX(new BigDecimal(request.getX()));
//        data.setY(new BigDecimal(request.getY()));
//        data.setZ(new BigDecimal(request.getZ()));
//        data.setRx(new BigDecimal(request.getRx()));
//        data.setRy(new BigDecimal(request.getRy()));
//        data.setRz(new BigDecimal(request.getRz()));
//        data.setReceiveTime(LocalDateTime.now());
//        data.setProcessStatus("SUCCESS");
//        return data;
//    }
//
//    /**
//     * 保存测量数据（模拟数据库操作）
//     */
//    private void saveMeasureData(MeasureData data) {
//        // 实际项目中应使用@Transactional注解保证事务性
//        String key = data.getZbxId() + "_" + System.currentTimeMillis();
//        measureDataStorage.put(key, data);
//
//        // 模拟数据持久化操作
//        System.out.printf(
//                "已保存测量数据: zbx_id=%s, x=%s, y=%s, z=%s%n",
//                data.getZbxId(), data.getX(), data.getY(), data.getZ()
//        );
//    }
//
//    /**
//     * 通知数据接收状态（模拟消息推送）
//     */
//    private void notifyDataReceived(MeasureData data) {
//        // 实际项目中可集成MQTT/Kafka等消息队列
//        String message = String.format(
//                "设备%s坐标数据已接收: X=%s, Y=%s, Z=%s",
//                data.getZbxId(), data.getX(), data.getY(), data.getZ()
//        );
//        System.out.println("发送通知: " + message);
//    }
//
//    /**
//     * 参数校验异常处理器
//     */
//    @ExceptionHandler(ConstraintViolationException.class)
//    public ResponseEntity<ApiResponse<Void>> handleConstraintViolation(ConstraintViolationException e) {
//        Set<ConstraintViolation<?>> violations = e.getConstraintViolations();
//        String errorMsg = violations.stream()
//                .map(v -> v.getPropertyPath() + ": " + v.getMessage())
//                .collect(Collectors.joining("; "));
//
//        ApiResponse<Void> response = new ApiResponse<>();
//        response.setCode(400);
//        response.setMessage("参数校验失败: " + errorMsg);
//
//        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
//    }
//
//    /**
//     * 通用异常处理器
//     */
//    @ExceptionHandler(Exception.class)
//    public ResponseEntity<ApiResponse<Void>> handleGeneralException(Exception e) {
//        ApiResponse<Void> response = new ApiResponse<>();
//        response.setCode(500);
//        response.setMessage("服务器处理失败: " + e.getMessage());
//
//        // 实际项目中应使用日志框架记录异常堆栈
//        e.printStackTrace();
//
//        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
//    }
//
//    // ------------------------------
//    // 内部静态类：请求与响应数据模型
//    // ------------------------------
//
//    /**
//     * 测量数据请求DTO
//     */
//    public static class MeasureRequest {
//        @NotBlank(message = "zbx_id不能为空")
//        @Pattern(regexp = ZBX_ID_PATTERN, message = "zbx_id格式错误，应为3-32位字母数字下划线组合")
//        private String zbx_id;
//
//        @NotBlank(message = "x坐标不能为空")
//        @Pattern(regexp = COORDINATE_PATTERN, message = "x坐标格式错误，支持正负整数或最多6位小数")
//        private String x;
//
//        @NotBlank(message = "y坐标不能为空")
//        @Pattern(regexp = COORDINATE_PATTERN, message = "y坐标格式错误，支持正负整数或最多6位小数")
//        private String y;
//
//        @NotBlank(message = "z坐标不能为空")
//        @Pattern(regexp = COORDINATE_PATTERN, message = "z坐标格式错误，支持正负整数或最多6位小数")
//        private String z;
//
//        @NotBlank(message = "rx角度不能为空")
//        @Pattern(regexp = ANGLE_PATTERN, message = "rx角度格式错误，支持正负整数或最多6位小数")
//        private String rx;
//
//        @NotBlank(message = "ry角度不能为空")
//        @Pattern(regexp = ANGLE_PATTERN, message = "ry角度格式错误，支持正负整数或最多6位小数")
//        private String ry;
//
//        @NotBlank(message = "rz角度不能为空")
//        @Pattern(regexp = ANGLE_PATTERN, message = "rz角度格式错误，支持正负整数或最多6位小数")
//        private String rz;
//
//        // Getters and Setters
//        public String getZbxId() { return zbx_id; }
//        public void setZbxId(String zbxId) { this.zbx_id = zbxId; }
//        public String getX() { return x; }
//        public void setX(String x) { this.x = x; }
//        public String getY() { return y; }
//        public void setY(String y) { this.y = y; }
//        public String getZ() { return z; }
//        public void setZ(String z) { this.z = z; }
//        public String getRx() { return rx; }
//        public void setRx(String rx) { this.rx = rx; }
//        public String getRy() { return ry; }
//        public void setRy(String ry) { this.ry = ry; }
//        public String getRz() { return rz; }
//        public void setRz(String rz) { this.rz = rz; }
//    }
//
//    /**
//     * 测量数据实体类
//     */
//    public static class MeasureData {
//        private String zbxId;
//        private BigDecimal x;
//        private BigDecimal y;
//        private BigDecimal z;
//        private BigDecimal rx;
//        private BigDecimal ry;
//        private BigDecimal rz;
//        private LocalDateTime receiveTime;
//        private String processStatus;
//
//        // Getters and Setters
//        public String getZbxId() { return zbxId; }
//        public void setZbxId(String zbxId) { this.zbxId = zbxId; }
//        public BigDecimal getX() { return x; }
//        public void setX(BigDecimal x) { this.x = x; }
//        public BigDecimal getY() { return y; }
//        public void setY(BigDecimal y) { this.y = y; }
//        public BigDecimal getZ() { return z; }
//        public void setZ(BigDecimal z) { this.z = z; }
//        public BigDecimal getRx() { return rx; }
//        public void setRx(BigDecimal rx) { this.rx = rx; }
//        public BigDecimal getRy() { return ry; }
//        public void setRy(BigDecimal ry) { this.ry = ry; }
//        public BigDecimal getRz() { return rz; }
//        public void setRz(BigDecimal rz) { this.rz = rz; }
//        public LocalDateTime getReceiveTime() { return receiveTime; }
//        public void setReceiveTime(LocalDateTime receiveTime) { this.receiveTime = receiveTime; }
//        public String getProcessStatus() { return processStatus; }
//        public void setProcessStatus(String processStatus) { this.processStatus = processStatus; }
//    }
//
//    /**
//     * 测量结果统计类
//     */
//    public static class MeasureResult {
//        private int totalCount;
//        private int successCount;
//        private int failCount;
//        private LocalDateTime processTime;
//        private List<String> errorDetails;
//
//        // Getters and Setters
//        public int getTotalCount() { return totalCount; }
//        public void setTotalCount(int totalCount) { this.totalCount = totalCount; }
//        public int getSuccessCount() { return successCount; }
//        public void setSuccessCount(int successCount) { this.successCount = successCount; }
//        public int getFailCount() { return failCount; }
//        public void setFailCount(int failCount) { this.failCount = failCount; }
//        public LocalDateTime getProcessTime() { return processTime; }
//        public void setProcessTime(LocalDateTime processTime) { this.processTime = processTime; }
//        public List<String> getErrorDetails() { return errorDetails; }
//        public void setErrorDetails(List<String> errorDetails) { this.errorDetails = errorDetails; }
//    }
//
//    /**
//     * 统一API响应类
//     */
//    public static class ApiResponse<T> {
//        private int code;
//        private String message;
//        private T data;
//
//        // Getters and Setters
//        public int getCode() { return code; }
//        public void setCode(int code) { this.code = code; }
//        public String getMessage() { return message; }
//        public void setMessage(String message) { this.message = message; }
//        public T getData() { return data; }
//        public void setData(T data) { this.data = data; }
//    }
//}
