import React, { useEffect, useState } from 'react';
import { Select, notification, Spin } from 'antd';
import { listSysMetadataSource } from '@/services/system/sysMetadata'


const { Option } = Select;


const MetaDataSelect = ({ multiple = false, showGroupNo = false, groupkey, onChange, value, style }) => {

    const [list, setList] = useState([]);

    const [tag, setTag] = useState('');
    const [minKeyLength, setMinKeyLength] = useState(0);

    const [loading, setLoading] = useState(false);


    useEffect(() => {
        if(!style?.width){
            style={... style, width: 200}; 
        }
        setLoading(true)
        listSysMetadataSource({ keyword: '', groupkey })
            .then(response => {
                if (response?.result) {
                    setList(response?.data?.metadatas ?? []);
                    setTag(response?.data?.tag ?? '');
                    setMinKeyLength(response?.data?.minKeyLength ?? 0);
                }
            }).catch(error => {
                console.error(error);
                notification.warn({
                    description: `获取元数据[${groupkey}]失败，请刷新重试！`,
                    message: '请刷新重试',
                })
            }
            ).finally(()=>setLoading(false));
    }, []);


    function valueChange(value) {
        if (!onChange) {
            return
        }
        if (value === undefined) {
            onChange(value);
            return;
        }
        onChange(+value);

    }

    function onSearch(val) {
    }

    return (
        <Spin spinning={loading}>
            <Select
                showSearch
                mode={multiple ? 'tags' : ''}
                placeholder="请选择"
                allowClear
                optionFilterProp="children"
                getPopupContainer={triggerNode => triggerNode.parentNode}
                onChange={(value) => valueChange(value)}
                value={Number.isInteger(value) ? value.toString() : undefined}
                // onFocus={onFocus}
                // onBlur={onBlur}
                // onSearch={(value) => valueChange(value)}
                filterOption={(input, option) => {
                    return option.children.join('').toLowerCase().indexOf(input) >= 0
                }}
            >
                {list.map((metadate) =>
                    <Option key={metadate.id}>
                        {showGroupNo && ('[' + tag + metadate.groupNo?.padStart(minKeyLength ?? 0, "0") + ']')}{metadate.label}
                    </Option>
                )
                }
            </Select>
        </Spin>
    );
};

export default MetaDataSelect
