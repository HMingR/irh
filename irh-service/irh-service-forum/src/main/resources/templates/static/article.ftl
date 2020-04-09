<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <!-- 响应式元标记 -->
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <title>article</title>
    <link href="https://cdn.bootcss.com/quill/2.0.0-dev.3/quill.snow.min.css" rel="stylesheet">
    <style type="text/css">
        .article_container{
            display: flex;
        }
        p{
            font-size: 14px;
            letter-spacing: 1px;
            color: #3d3d3d;
            /* float: left; */
        }
        /*img{
            !* 图片上需要加上自适应 *!
            display: block;
            height: auto;
            width: 30%;
            !* max-width: 100%; *!
            margin: 0.5rem;
            float: left;
        }*/
    </style>
</head>
<body>
<div class="article_container">
    <!-- 这个div不要删   只需要替换内部的代码就可以了 -->
    <div>
        ${context}
    </div>
</div>
</body>
</html>