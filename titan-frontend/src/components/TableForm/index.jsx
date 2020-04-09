import React, { useContext, useState, useEffect, useRef } from 'react';
import { Table, Input, Button, Popconfirm, Form } from 'antd';

import MetaDataSelect from '@/components/MetaDataSelect'

const EditableContext = React.createContext();

const EditableRow = ({ index, ...props }) => {
  const [form] = Form.useForm();
  return (
    <Form form={form} component={false}>
      <EditableContext.Provider value={form}>
        <tr {...props} />
      </EditableContext.Provider>
    </Form>
  );
};

const EditableCell = ({
  title,
  editable,
  children,
  dataIndex,
  record,
  handleSave,
  ...restProps
}) => {
  const [editing, setEditing] = useState(false);
  const inputRef = useRef();
  const form = useContext(EditableContext);
  useEffect(() => {
    if (editing) {
      inputRef.current.focus();
    }
  }, [editing]);

  const toggleEdit = () => {
    setEditing(!editing);
    form.setFieldsValue({
      [dataIndex]: record[dataIndex],
    });
  };

  const save = async e => {
    try {
      const values = await form.validateFields();
      toggleEdit();
      handleSave({ ...record, ...values });
    } catch (errInfo) {
      console.log('Save failed:', errInfo);
    }
  };

  let childNode = children;

  if (editable) {
    childNode = editing ? (
      <Form.Item
        style={{
          margin: 0,
        }}
        name={dataIndex}
        rules={[
          {
            required: true,
            message: `${title} is required.`,
          },
        ]}
      >
        <Input ref={inputRef} onPressEnter={save} onBlur={save} />
      </Form.Item>
    ) : (
        <div
          className="editable-cell-value-wrap"
          style={{
            paddingRight: 24,
          }}
          onClick={toggleEdit}
        >
          {children}
        </div>
      );
  }

  return <td {...restProps}>{childNode}</td>;
};


const TableForm = ({ value, onChange }) => {
  const [dataSource, setDataSource] = useState(value);
  const [loading, setLoading] = useState(false);
  const [index, setIndex] = useState(1);

  useEffect(() => {
    let tempData = [
      {
        key: '0',
        propertyName: 'Edward King 0',
        propertyValue: '32',
        unitName: 'London, Park Lane no. 0',
      },
    ]
    setDataSource(tempData);
  }, []);

  

  const columns = [
    {
      title: '属性类型',
      dataIndex: 'propertyName',
      render: (_, record) => (
        <MetaDataSelect style={{ width: 150 }} groupkey="STEP_PROPERTY_TYPE" />
      ),
    },
    {
      title: '属性值',
      dataIndex: 'propertyValue',
      editable: true,
    },
    {
      title: '单位',
      dataIndex: 'unitName',
      render: (_, record) => (
        <MetaDataSelect style={{ width: 150 }} groupkey="STEP_PROPERTY_UNIT" />
      ),
    },
    {
      title: '操作',
      dataIndex: 'operation',
      render: (text, record) =>
        dataSource.length >= 1 ? (
          <Popconfirm title="Sure to delete?" onConfirm={() => handleDelete(record.key)}>
            <a>Delete</a>
          </Popconfirm>
        ) : null,
    },
  ];

  const handleDelete = (key) => {
    const newData = dataSource.filter(item => item.key !== key);
    setDataSource(newData);
  };

  const handleAdd = () => {
    const newData = {
      key: index,
      propertyName: '1',
      propertyValue: '2',
      unitName:'3',
    };
    setIndex(index + 1);
    setDataSource([...dataSource,newData]);
  };

  const handleSave = (row) => {
    const newData = dataSource;
    const index = newData.findIndex(item => row.key === item.key);
    const item = newData[index];
    newData.splice(index, 1, { ...item, ...row });
    setDataSource(newData);
  };


  
  const components = {
    body: {
      row: EditableRow,
      cell: EditableCell,
    },
  };
  // const columns = this.columns.map(col => {
  //   if (!col.editable) {
  //     return col;
  //   }

  //   return {
  //     ...col,
  //     onCell: record => ({
  //       record,
  //       editable: col.editable,
  //       dataIndex: col.dataIndex,
  //       title: col.title,
  //       handleSave: this.handleSave,
  //     }),
  //   };
  // });

  return (
    <div>
      <Button
        onClick={handleAdd}
        type="primary"
        style={{
          marginBottom: 16,
        }}
      >
        新增属性
        </Button>
      <Table
        components={components}
        rowClassName={() => 'editable-row'}
        bordered
        dataSource={dataSource}
        columns={columns}
      />
    </div>
  );
};

export default TableForm;