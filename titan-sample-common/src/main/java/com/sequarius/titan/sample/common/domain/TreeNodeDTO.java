package com.sequarius.titan.sample.common.domain;

import lombok.Data;

import java.util.List;

/**
 * project erp-bundle
 * ANT DESIGN 树形选择控件Node https://ant.design/components/tree-cn/
 * @author Sequarius *
 * @since 02/03/2020
 */
@Data
public class TreeNodeDTO {
    private String title;
    private String key;
    private String value;
    private Boolean selectable;
    private Boolean disabled;
    private Boolean disableCheckbox;
    private List<TreeNodeDTO> children;
}
