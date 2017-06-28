/*
 * Copyright 2016, Stuart Douglas, and individual contributors as indicated
 * by the @authors tag.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package a.org.fakereplace.test.replacement.annotated.field;

import java.lang.reflect.Field;

import org.junit.Assert;
import org.junit.Test;
import a.org.fakereplace.test.util.ClassReplacer;

public class AnnotatedFieldTest {

    @Test
    public void testFieldAnnotations() throws SecurityException, NoSuchFieldException {
        ClassReplacer r = new ClassReplacer();
        r.queueClassForReplacement(FieldAnnotated.class, FieldAnnotated1.class);
        r.replaceQueuedClasses();

        Field m1 = FieldAnnotated.class.getField("field1");
        Field m2 = FieldAnnotated.class.getField("field2");
        Field m3 = FieldAnnotated.class.getField("field3");
        Assert.assertEquals("1", m1.getAnnotation(FieldAnnotation.class).value());
        Assert.assertFalse(m2.isAnnotationPresent(FieldAnnotation.class));
        Assert.assertEquals("3", m3.getAnnotation(FieldAnnotation.class).value());

    }

}
