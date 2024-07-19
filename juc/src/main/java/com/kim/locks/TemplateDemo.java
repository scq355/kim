package com.kim.locks;

import com.kim.utils.Print;

public class TemplateDemo {

    static abstract class AbstractAction {
        public void tempMethod() {
            Print.cfo("模板方法的算法骨架被执行");
            beforeAction();
            action();
            afterAction();
        }

        protected void beforeAction() {
            Print.cfo("准备执行钩子方法");
        }

        public abstract void action();

        private void afterAction() {
            Print.cfo("钩子方法执行完成");
        }
    }


    static class ActionA extends AbstractAction {
        @Override
        public void action() {
            Print.cfo("钩子方法实现，ActionA.action()被执行");
        }
    }

    static class ActionB extends AbstractAction {
        @Override
        public void action() {
            Print.cfo("钩子方法实现，ActionB.action()被执行");
        }
    }

    public static void main(String[] args) {
        AbstractAction action = null;

        action = new ActionA();
        action.tempMethod();

        action = new ActionB();
        action.tempMethod();
    }
}
