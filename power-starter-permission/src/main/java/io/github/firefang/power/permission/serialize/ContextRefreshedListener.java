package io.github.firefang.power.permission.serialize;

import java.lang.reflect.Method;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.springframework.aop.support.AopUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

import io.github.firefang.power.permission.annotations.Permission;
import io.github.firefang.power.permission.annotations.PermissionGroup;

/**
 * 程序启动时收集Controller中的权限声明并写入数据库
 * 
 * @author xinufo
 *
 */
public class ContextRefreshedListener implements ApplicationListener<ContextRefreshedEvent> {
    private IPermissionService permissionService;

    public ContextRefreshedListener(IPermissionService permissionService) {
        this.permissionService = permissionService;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        ApplicationContext context = event.getApplicationContext();
        // 根容器为Spring容器
        if (context.getParent() == null) {
            Map<String, Object> beans = context.getBeansWithAnnotation(PermissionGroup.class);
            List<PermissionDO> perms = new LinkedList<>();
            for (Object bean : beans.values()) {
                addPermsToList(bean, perms);
            }
            permissionService.upsertBatch(perms);
        }
    }

    private void addPermsToList(Object bean, List<PermissionDO> perms) {
        Class<?> clazz = AopUtils.getTargetClass(bean); // 处理动态代理
        if (clazz.isAnnotationPresent(RestController.class) || clazz.isAnnotationPresent(Controller.class)) {
            String group = clazz.getAnnotation(PermissionGroup.class).value();
            for (Method m : clazz.getMethods()) {
                Permission anno = m.getAnnotation(Permission.class);
                if (anno != null) {
                    PermissionDO perm = new PermissionDO();
                    perm.setGroup(group);
                    perm.setName(anno.value());
                    perms.add(perm);
                }
            }
        }
    }

}
