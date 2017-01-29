### Custom View 记录

#### 核心说明
* 一

```android

        //主Path构造
        mPath = new Path();
        mPath.addCircle(0, 0, mRadius, Path.Direction.CW);
        mPath.close();

        //目标 path构造
        mDstPath = new Path();
        mDst2Path = new Path();

        //Path测量
        mPathMeasure = new PathMeasure(mPath, false);
        //绘制总长度
        mLength = mPathMeasure.getLength();

```

* 二

```android
        //测量Path路径上的绘制终点的 坐标 与 正切值
        //参数一  测量Path的终点
        //参数二  终点坐标数组
        mPathMeasure.getPosTan((i + 1) * unit, mPos1, mTan);
        float x = mPos1[0];
        float y = mPos1[1];
        //绘制小圆
        convas.drawCircle(x, y, mRadiusWidth / 2, mPaint);


```
