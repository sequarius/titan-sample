package com.sequarius.titan.sample.system.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * project erp-bundle
 *
 * @author JiangDingMing *
 * @since 23/03/2020
 */
@ApiModel("元数据组选择器响应")
@Data
public class MetaDataSelectResponseDTO {

    @ApiModelProperty("元数据组标签")
    private String tag;

    @ApiModelProperty(value = "最小ID长度")
    private Long minKeyLength;

    @ApiModelProperty(value = "数据长度")
    private List<SysMetadataResponseDTO> metadatas;
}
