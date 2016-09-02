# ScrollPager
利用ViewPager实现每个Fragment的翻转
##DEMO
![](https://raw.githubusercontent.com/youmu178/ScrollPager/master/scrollpager.gif)

代码有注释，很好理解
###[首先理解ViewPager动画的实现原理](http://www.jcodecraeer.com/a/anzhuokaifa/androidkaifa/2015/0115/2314.html)
从3.0开始，ViewPager开始支持自定义切换动画，暴露的接口为PageTransformer，因此只要实现PageTransformer接口和其唯一的方法transformPage(View view, float position)即可。
```java
public interface PageTransformer {
        /**
         * Apply a property transformation to the given page.
         *
         * @param page Apply the transformation to this page
         * @param position Position of page relative to the current front-and-center
         *                 position of the pager. 0 is front and center. 1 is one full
         *                 page position to the right, and -1 is one page position to the left.
         */
        public void transformPage(View page, float position);
}
```
* 参数position
给定界面的位置相对于屏幕中心的偏移量。在用户滑动界面的时候，是动态变化的。那么我们可以将position的值应用于setAlpha(), setTranslationX(), or setScaleY()方法，从而实现自定义的动画效果。
另 外在ViewPager滑动时，内存中存活的Page都会执行transformPage方法，在滑动过程中涉及到两个Page，当前页和下一页，而它们 的position值是相反的（因为是相对运动,一个滑入一个滑出），比如，页面A向右滑动到屏幕一半，页面B也正好处于一半的位置，那么A和B的 position为：0.5 和 -0.5
position ==  0 ：当前界面位于屏幕中心的时候
position ==  1 ：当前Page刚好滑出屏幕右侧
position == -1 ：当前Page刚好滑出屏幕左侧

