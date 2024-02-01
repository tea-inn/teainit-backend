package top.teainn.project.model.dto.log.city;

import lombok.Data;

@Data
public class ResultData {
    private String ip;
    private Object location;
    private AdInfoData ad_info;
}
