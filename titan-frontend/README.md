# Ant Design Pro

This project is initialized with [Ant Design Pro](https://pro.ant.design). Follow is the quick guide for how to use.

## 前置准备

### 安装yarn 
```
https://github-production-release-asset-2e65be.s3.amazonaws.com/49970642/70893a80-47d1-11ea-9eee-ccc71dcabcba?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Credential=AKIAIWNJYAX4CSVEH53A%2F20200208%2Fus-east-1%2Fs3%2Faws4_request&X-Amz-Date=20200208T075101Z&X-Amz-Expires=300&X-Amz-Signature=21d86374c707933155834b9470704f5c42ae102daa8f9c7ab34b21fa15a5c640&X-Amz-SignedHeaders=host&actor_id=7238573&response-content-disposition=attachment%3B%20filename%3Dyarn-1.22.0.msi&response-content-type=application%2Foctet-stream

```
### 设置淘宝源
```
yarn config set registry https://registry.npm.taobao.org/
yarn config set puppeteer_download_host https://npm.taobao.org/mirrors
```
### 安装GYP
```
https://github.com/nodejs/node-gyp#on-windows
```


## Environment Prepare

Install `node_modules`:

```bash
npm install
```

or

```bash
yarn
```

## Provided Scripts

Ant Design Pro provides some useful script to help you quick start and build with web project, code style check and test.

Scripts provided in `package.json`. It's safe to modify or add additional script:

### Start project

```bash
npm start
```

### Build project

```bash
npm run build
```

### Check code style

```bash
npm run lint
```

You can also use script to auto fix some lint error:

```bash
npm run lint:fix
```

### Test code

```bash
npm test
```

## More

You can view full document on our [official website](https://pro.ant.design). And welcome any feedback in our [github](https://github.com/ant-design/ant-design-pro).
