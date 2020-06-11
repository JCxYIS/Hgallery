# Hgallery
###### My final project of NCU 計算機實習 (Introduction to Computer Science - Lab)

![Java CI with Maven](https://github.com/JCxYIS/NCU-CSLab-FinalProject/workflows/Java%20CI%20with%20Maven/badge.svg)

### 目錄
[TOC]

### 開啟方法
開啟到 hgallery 專案資料夾
```
cd hgallery
```
執行
```
mvn clean javafx:run
```

### 特色
- 閱讀相片
- 人性化的操作方式
- 可以調整視窗大小，想怎麼看就怎麼看

- 最重要的功能：與nhentai連動，歡樂閱讀本本
- H功能上鎖：怕你家未成年小朋友看到，害你被抓去關？把它鎖起來就好啦 (不是指小朋友)
- 支援連接Discord RPC，向朋友顯示你在看什麼！

### 使用說明
#### 主畫面
- 點擊左方的功能列以開啟需要的功能

#### 相簿選擇
- 瀏覽敏感內容時會模糊
- 滑鼠
	- 左鍵點擊：閱讀相簿
	- 右鍵點擊：(本子)加到稍後閱讀 [注意：重新啟動程式會清空稍後閱讀清單]
	<!-- - 滾輪點擊：(本子)儲存本子 [尚未實裝] -->

#### 相片閱讀器
- 瀏覽敏感內容時會模糊
- 滑鼠
    - 點擊：翻頁 (由畫面的左右判斷)
    - 拖曳：調整位置
    - 滾輪：調整位置 (垂直)
    - Ctrl + 滾輪：縮放
- 鍵盤
    - 左、右：翻頁
    - 上、下：調整位置 (垂直)
    - ＋、ㄧ：縮放
    - F11：全螢幕
    - Esc：離開

#### 設定
- 查看可設定的參數
- 更改密碼：需要輸入原本密碼才能更改，否則會跳出錯誤
- 點擊「儲存」才會存檔
- 存檔位置：`{使用者位置}/Hgallery/save.txt`

#### 密碼輸入
- 輸入密碼才能繼續
- 如果還沒設定密碼，會先要求設定

#### 退斯妥也老司機
- 敏感內容：會模糊
- 輸入六位車號 / 本子名稱



### 參考資料
[See here](hgallery/References.md)

### 後記
> Hgallery是一款真正的本子瀏覽器


