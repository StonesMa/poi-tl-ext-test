/*
 * Copyright 2016 - 2021 Draco, https://github.com/draco1023
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package poi.common;

import com.deepoove.poi.XWPFTemplate;
import com.deepoove.poi.config.Configure;
import com.deepoove.poi.data.*;
import com.deepoove.poi.plugin.table.LoopRowTableRenderPolicy;
import com.deepoove.poi.xwpf.NumFormat;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

class PoitlRenderPolicyTest {

    @Test
    void doRender() throws IOException {
        Configure configure = Configure.builder()
                .bind("files", new LoopRowTableRenderPolicy())
                .build();
        Map<String, Object> data = new HashMap<>(2);
        // 普通文本
        data.put("var1", "文本一");
        data.put("var2", "文本二");
        data.put("var3", "马磊");
        data.put("var4", "曹令");
        // 表格渲染（1.10.0+）
        List<File> files = new ArrayList<>();
        files.add(new File(1,"附件1"));
        files.add(new File(2,"附件2"));
        files.add(new File(3,"附件3"));
        files.add(new File(4,"附件4"));
        files.add(new File(5,"附件5"));
        data.put("files", files);
        // 表格渲染（1.10.0-
        /*RowRenderData row0 = Rows.of("序号", "文件名").textColor("FFFFFF")
                .bgColor("4472C4").center().create();
        RowRenderData[] rowArray = new RowRenderData[files.size()+1] ;
        rowArray[0] = row0;
        for (int i = 0; i < files.size(); i++) {
            File file = files.get(i);
            RowRenderData row = Rows.of(String.valueOf(file.getNum()), file.getFileName())
                    .textFontFamily("仿宋")
                    .textFontSize(14)
                    .center()
                    .create();
            rowArray[i+1] = row;
        }
        data.put("table1", Tables.of(rowArray).width(13f, new double[]{13/6,13*6/6}).create());*/


        // 列表特殊符号
        //列表集合
        List<String> varList = Arrays.asList("序号1","序号2","序号3");
        //自定义序号的样式为  ✓
        NumberingFormat numberingFormat = new NumberingFormat(NumFormat.BULLET, "✓");
        //自定义序号的样式为  a)  b)  c)
        /*NumberingFormat.Builder builder = NumberingFormat.
                builder("%{0})") //%{0}) 可以指定自己需要的样式
                .withNumFmt(NumFormat.LOWER_LETTER);  //小写字母
        NumberingFormat numberingFormat = builder.build(0);
        Numberings.NumberingBuilder of = Numberings.of(numberingFormat);//a)  b)  c)*/
        Numberings.NumberingBuilder of = Numberings.of(numberingFormat);
        //列表 数据赋值
        varList.forEach(s -> of.addItem(s));
        NumberingRenderData numberingRenderData = of.create();
        data.put("listBullet", numberingRenderData);

        // 区块对
        data.put("songs", Arrays.asList("✓song1", "✓song2", "✓song3"));

        try (InputStream inputStream = PoitlRenderPolicyTest.class.getResourceAsStream("/poi-tl-test-common.docx")) {
            XWPFTemplate.compile(inputStream, configure).render(data).writeToFile("poi-tl-test-common-out.docx");
        }
    }

}