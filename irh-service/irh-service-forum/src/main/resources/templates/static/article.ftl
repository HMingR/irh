<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <!-- 响应式元标记 -->
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

    <title>article</title>
    <link href="https://cdn.bootcss.com/quill/2.0.0-dev.3/quill.snow.css" rel="stylesheet">
    <link href="https://cdn.bootcss.com/quill/2.0.0-dev.3/quill.bubble.css" rel="stylesheet">
    <link href="https://cdn.bootcss.com/quill/2.0.0-dev.3/quill.core.css" rel="stylesheet">
    <style type="text/css">
        .article_container{
            display: flex;
        }
        p{
            font-size: 14px;
            letter-spacing: 1px;
            color: #3d3d3d;
        }
        /*滚动条样式*/
        html::-webkit-scrollbar {
            /*滚动条整体样式*/
            width: 3px; /*高宽分别对应横竖滚动条的尺寸*/
            height: 3px;
        }
        html::-webkit-scrollbar-thumb {
            /*滚动条里面小方块*/
            border-radius: 3px;
            -webkit-box-shadow: inset 0 0 5px rgba(197, 197, 197, 0.6);
            background: rgba(0, 0, 0, 0.6);
        }
        html::-webkit-scrollbar-track {
            /*滚动条里面轨道*/
            border-radius: 3px;
            -webkit-box-shadow: inset 0 0 5px rgba(223, 223, 223, 0.4);
            border-radius: 0;
            background: rgba(1, 1, 1, 0.2);
        }
        /* 覆盖一个自带的样式 */
        /* white-space: pre-line */
        .ql-editor{
            white-space: normal;
        }
    </style>
</head>
<body>
<div class="article_container">
    <div style='width:100%;'>
        <div class='quill-editor'>
            <div class="ql-container">
                <div class='ql-editor'>
                    <!-- 这个div不要删   只需要替换内部的代码就可以了 -->
                    ${context}
                </div>
            </div>
        </div>
    </div>
</body>
</html>