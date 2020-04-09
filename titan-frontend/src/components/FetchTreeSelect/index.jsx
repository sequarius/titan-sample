import React, { useEffect, useState } from 'react';
import { TreeSelect, notification, Spin } from 'antd';
const { SHOW_CHILD } = TreeSelect;


const FetchTreeSelect = ({ multiple = false, onChange, value, style, loadMethod }) => {

    const defaultStyle = { width: 200 };

    const [treeData, setTreeData] = useState([]);

    const [loading, setLoading] = useState(false);

    const [innerValue, setInnerValue] = useState(null);

    var groupBy = function (items) {
        let result = {};
        for (let item of items) {
            result[item.title] = item;
        }
        return result;
    };

    function loadData(keyword = null, ids = []) {
        if (keyword === null && !ids.length === 0 || !loadMethod) {
            return;
        }
        loadMethod({ keyword, ids: ids.join(',') })
            .then(response => {
                if (!response?.result) {
                    return
                }
                let data = groupBy(response?.data ?? []);
                let result = [];
                for (let departmentNode of treeData) {
                    let datagroup = data[departmentNode.title];
                    if (!datagroup) {
                        result.push(departmentNode);
                        continue;
                    }
                    let childMap = groupBy(departmentNode?.children ?? []);
                    for (let child of datagroup.children ?? []) {
                        if (childMap[child.title]) {
                            continue;
                        }
                        departmentNode.children.push(child);
                    }

                    result.push(departmentNode);
                    delete data[departmentNode.title];
                }
                result = result.concat(Object.values(data ?? []));
                setTreeData(result);
            }).catch(error => {
                console.error(error);
                notification.warn({
                    description: `获取列表失败，请重试！`,
                    message: '请刷新重试',
                })
            }
            ).finally(() => setLoading(false));
    }


    useEffect(() => {
        if (!value) {
            return;
        }
        setLoading(true);
        if (!Array.isArray(value)) {
            loadData('', [value.toString()]);
            return;
        }
        loadData('', value.map(String));
    }, []);


    function valueChange(value) {

        let selectValue;
        if (typeof value !== 'string') {
            selectValue = value;
        } else if (!value.includes(",")) {
            selectValue = +value;
        } else {
            selectValue = value.split`,`.map(x => +x);
        }

        if (!onChange) {
            setInnerValue(selectValue);
        } else {
            onChange(selectValue);
        }
    }

    function onSearch(keyword) {
        if (keyword.length === 0) {
            return;
        }
        loadData(keyword)
    }

    function getValue() {
        let returnVal = value;
        if (innerValue !== null) {
            returnVal = innerValue;
        }
        if (!returnVal) {
            return returnVal;
        }
        if (!Array.isArray(returnVal)) {
            return [returnVal.toString()];
        }

        return returnVal.map(String);
    }

    return (
        <Spin spinning={loading}>
            <TreeSelect
                showSearch
                style={ {...defaultStyle, ... style}}
                placeholder="输入关键字搜索"
                allowClear
                showCheckedStrategy={SHOW_CHILD}
                getPopupContainer={triggerNode => triggerNode.parentNode}
                onChange={(value) => valueChange(value)}
                value={getValue()}
                treeData={treeData}
                treeDefaultExpandAll
                treeCheckable={multiple}
                onSearch={(value) => onSearch(value)}
                filterOption={(input, option) => {
                    return true
                }}
            />
        </Spin>
    );
};

export default FetchTreeSelect