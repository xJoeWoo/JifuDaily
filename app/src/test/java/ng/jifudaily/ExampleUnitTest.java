package ng.jifudaily;

import org.junit.Test;
import org.reactivestreams.Subscription;

import ng.jifudaily.support.ioc.component.DaggerServiceComponent;
import ng.jifudaily.support.ioc.component.ServiceComponent;
import ng.jifudaily.support.net.bean.LatestNewsBean;
import ng.jifudaily.support.tool.SubscriberAdapter;
import ng.jifudaily.test.ServicesContainer;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void Test() throws Exception {
//
//        try {
//            ServiceComponent s = DaggerServiceComponent.create();
//            ServicesContainer c = new ServicesContainer();
//            s.injectTo(c);
//
//            c.getServices().daily().getLatestNews(new SubscriberAdapter<LatestNewsBean>() {
//
//                @Override
//                public void onSubscribe(Subscription s) {
//                    System.out.println("S");
//                    super.onSubscribe(s);
//                }
//
//                @Override
//                                public void onComplete() {
//                    System.out.println("Com");
//
//                }
//
//                @Override
//                public void onNext(LatestNewsBean latestNewsBean) {
//                    System.out.println(latestNewsBean.getStories().get(1).getTitle());
//                }
//
//                @Override
//                public void onError(Throwable t) {
//                    System.out.println(t.getMessage());
//                }
//            });
//            Thread.sleep(2000);
//        } catch (Exception e) {
//            System.out.println(e.getMessage());
//
//        }


    }
}