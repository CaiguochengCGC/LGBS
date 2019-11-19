/**
 * 公告方法JS
 */

Ext.namespace('ht.pub');
Ext.TaskMgr.stopAll();

/**
 * 进度条提示框
 */
ht.pub.progress = function(amsg) {
    Ext.MessageBox.show({
        title: ht.msg.msgbox.processTitle,
        msg: amsg,
        progressText: ht.msg.msgbox.progressText,
        progress:true,
        closable:false,
        icon : 'large-loading'
    });
}

/**
 * 等待提示框
 */
ht.pub.wait = function(amsg) {
    Ext.MessageBox.show({
        title : ht.msg.msgbox.waitTitle,
        msg : amsg,
        progressText : '...',
        wait : true,
        waitConfig : {
            interval : 200
        },
        icon : 'large-loading'
    });
}

/**
 * 信息提示框
 * @param {String} amsg 提示信息(*)
 * @param {function} afun 确定后执行函数
 * @param {Boolean} autoHide 自动隐藏，默认值为true
 */
ht.pub.info = function(amsg, afun, autoHide){
    Ext.MessageBox.show({
        title:ht.msg.msgbox.infoTitle,
        msg: amsg,
        buttons: Ext.MessageBox.OK,
        fn: afun,
        icon: Ext.Msg.INFO
    });
    
    if (autoHide !== false) {
       setTimeout(function(){
           if (Ext.Msg.getDialog('').title == ht.msg.msgbox.infoTitle) {
               Ext.Msg.hide();
               if(afun) {
                   afun();
               }
           }
       }, ht.config.params.MSGBOX_SHOW_TIME);
    }
}

/**
 * 警告提示框
 * @param {String} amsg 提示信息(*)
 * @param {function} afun 确定后执行函数
 */
ht.pub.warning = function(amsg, afun){
    Ext.MessageBox.show({
        title:ht.msg.msgbox.waringTitle,
        msg: amsg,
        buttons: Ext.MessageBox.OK,
        fn: afun,
        icon: Ext.Msg.WARNING
    });
}

/**
 * 错误提示框
 * @param {String} amsg 提示信息(*)
 * @param {function} afun 确定后执行函数
 */
ht.pub.error = function(amsg, afun){
    Ext.MessageBox.show({
        title:ht.msg.msgbox.errorTitle,
        msg: amsg,
        buttons: Ext.MessageBox.OK,
        fn: afun,
        icon: Ext.Msg.ERROR
    });
}

/**
 * 确认提示框
 * @param {String} amsg 提示信息(*)
 * @param {function} afun(btn) 确定后执行函数;btn：变量。点击‘是’，值为'yes'
 */
ht.pub.confirm = function(amsg, afun){
    Ext.Msg.confirm(ht.msg.msgbox.confirmTitle, amsg, afun);
}

/**
 * 禁用键盘事件
 * @param {Number} key
 * @param {Ext.EventObject} event
 */
ht.pub.stopKeyEvent = function(key, event){
    try{
        event.stopEvent();
        if(Ext.isIE){
             event.browserEvent.keyCode = 0;
        }
    }catch(e){
    
    }
} 

/**
 * 忽略某个键
 * @param {Number} key
 * @param {Ext.EventObject} event
 */
ht.pub.ingoreKeyEvent = function(key, event) {
    var targer =  event.target;
    var isStopEvent = true; 

    // 事件源有type属性，并且是text，textarea、password
    if(targer.type) {
        if('text' == targer.type || 'textarea' == targer.type || 'password' == targer.type) {
            if(!(targer.readOnly || targer.disabled)){ // 是可编辑的
                isStopEvent = false;
                return;
            }
        }
    }
    if(isStopEvent){
        ht.pub.stopKeyEvent(key, event);
    }
}

ht.pub.KEY_EVENT_ID = {};

/**
 * 禁用某些按键
 * @param {String/Object} el
 */
ht.pub.ingoreKeysEvent = function(el) {
    
    if('string' == typeof el) {
       if (ht.pub.KEY_EVENT_ID[el]) {
           return;
       }
       ht.pub.KEY_EVENT_ID[el] = true;
    }
    
    new Ext.KeyMap(el, [{  
         key : Ext.EventObject.BACKSPACE,// Backspace
         fn : function(key, event) {  // 只去除Backspace后退功能，不去除其删除功能
             ht.pub.ingoreKeyEvent(key, event);
        },
        scope : this
     },{   
        key : Ext.EventObject.F5,   
        fn : ht.pub.stopKeyEvent,   
        scope : this
    },{   
        key : Ext.EventObject.R,   
        ctrl : true,   
        fn : ht.pub.stopKeyEvent,   
        scope : this
    }]);
}

Ext.namespace('ht.pub.store');
ht.pub.store.BLANK_TEXT = '　'; //全角空格

/**
 * 克隆json类型的store
 * @param {Ext.data.JsonStore} store json类型的store,被克隆的store
 * @param {Ext.data.Store} storeClone json类型的store，要克隆的store    
 *  var storeClone = new Ext.data.Store({ 
 *     recordType: store.recordType 
 *  });
 */
ht.pub.store.cloneStore = function(store, storeClone) {
    var records = []; 
    store.each(function(r){ 
        records.push(r.copy()); 
    }); 
    
    storeClone.add(records);
}

/**
 * store第一条数据增加空格
 * @param {Ext.data.Store} store Store
 * @param {String} valueField 值字段，默认值为VALUE
 * @param {String} textField 文本字段，默认值为TEXT
 */
ht.pub.store.addBlankText = function(store, valueField, textField) {
   
    //设置默认值
    if (!valueField) {
        valueField = 'VALUE';
    }
    if (!textField) {
        textField = 'TEXT';
    }
    
     //增加空格数据
    var comboRecord = Ext.data.Record.create([valueField, textField]);
    var value = {};
    value[valueField] = '';
    value[textField] = ht.pub.store.BLANK_TEXT;
    var record = new comboRecord(value);
    
    store.insert(0, record);
}

Ext.namespace('ht.pub.excel');
ht.pub.excel.WIDTH_RATION = 1 / 8; ////表格列宽度与同宽度的excel列的比率
ht.pub.excel.ROW_HEIGHT = 18; //导出Excel的行高
ht.pub.excel.HEAD_COLOR = 0x00c0ff; //表头背景颜色 #ffc000的逆ff c0 00 ->  00 c0 ff -> 00c0ff
ht.pub.excel.HEAD_FONT_BOLD = true; //excel表头字体加粗
ht.pub.excel.FONT_NAME = '宋体';
ht.pub.excel.ROW_SPAN = '_EXCEL_ROW_SPAN';

/**
 * config:{grid:GridPanel, sheetName:string}
 */
Ext.Excel = function(config){
    Ext.apply(this,config);
};
Ext.apply(Ext.Excel.prototype,{
    gridToExcel : function(){ 
        var excelObj, workbookObj;

        try{  
            //初始化参数
            excelObj = new ActiveXObject('Excel.Application');   
            workbookObj = excelObj.Workbooks.Add();   
            var sheetObj = workbookObj.ActiveSheet;  
            
            //获取表格参数
            var grid = this.grid;  
            var recordCount = this.grid.getStore().getCount();  
            var ingoreCols = this.ingoreCols || [];
            var gridView = grid.getView();  
            var gridColModel = grid.getColumnModel();  
            var colCount = gridColModel.getColumnCount() - 1;  
            var showedGridCols = []; 
            
            
            //获取未隐藏列
            for(var i = 1; i <= colCount; i++){  
                if(!gridColModel.isHidden(i) 
                    && !(gridColModel.columns[i] instanceof Ext.grid.CheckboxSelectionModel)
                    && ingoreCols.indexOf(i) == -1){  
                    showedGridCols.push(i);  
                }  
            } 
            
            //设置border和垂直排列
            var aAscii = 97; //A
            var A2ZColLength = 26;
            var preColCode = ''; //列的字母A-Z, AA-AZ, BA-BZ
            
            //获取列的开头字母
            var startColNum = aAscii - 1 + parseInt((showedGridCols.length - 1) / A2ZColLength); //A-Z共有26个字母
            if (startColNum >= aAscii) {
               preColCode = String.fromCharCode(startColNum)
            }
            
            //设置多表头（除了最后一行）
            var headGroupRows = 1; //表头行数
            var groupPlugs = grid.plugins;
            if (!Ext.isEmpty(groupPlugs)) {
                
                //获取多表头插件
                var groupPlug;
                for (var i = 0; i < groupPlugs.length; i++) {
                    if (groupPlugs[i] instanceof Ext.ux.grid.ColumnHeaderGroup) {
                        groupPlug = groupPlugs[i];
                        break;
                    }
                }
                
                //多表头写入excel
                if (!Ext.isEmpty(groupPlug)) {
                    var groupRows = groupPlug.config.rows;
                    headGroupRows += groupRows.length;
                    
                    for (var i = 0; i < groupRows.length; i++) {
                        var gridColIndex = 1;
                        var excelColIndex = 1;
                        var groupRow = groupRows[i];
                        for (var k = 1; k < groupRow.length; k++) {
                            
                            //获取分组信息
                            var gridColumn = groupRow[k];
                            gridColumn.colspan = gridColumn.colspan || 1;
                            
                            //获取实际合并数
                            var excelColspan = 0;
                            for (var kk = 0; kk < gridColumn.colspan; kk++) {
                                if(!gridColModel.isHidden(gridColIndex + kk) 
                                    && !(gridColModel.columns[gridColIndex + kk] instanceof Ext.grid.CheckboxSelectionModel)
                                    && ingoreCols.indexOf(gridColIndex + kk) == -1){  
                                
                                    excelColspan++;
                                } 
                            }
                            
                            //合并处理
                            if (excelColspan > 0) {
                                sheetObj.Cells(i + 1, excelColIndex).value = gridColumn.header;
                                if (excelColspan > 1) {
                                    sheetObj.Range(sheetObj.Cells(i + 1, excelColIndex), sheetObj.Cells(i + 1, excelColIndex + excelColspan - 1)).MergeCells = true;
                                }
                                excelColIndex += excelColspan;
                            }
                            
                            gridColIndex += gridColumn.colspan;
                        }
                    }
                }
            }
            
            //最后一行表头
            for(var i = 1; i <= showedGridCols.length;i++){  
                sheetObj.Cells(headGroupRows, i).value = gridColModel.getColumnHeader(showedGridCols[i - 1]).replace(new RegExp('<br[ ]*/>','gi'),'\r\n');
                sheetObj.Columns(i).ColumnWidth = gridColModel.getColumnWidth(showedGridCols[i - 1]) * ht.pub.excel.WIDTH_RATION;
            } 
            
            //设置表头、内容样式
            var endColCode = String.fromCharCode(aAscii + (showedGridCols.length - 1) % A2ZColLength);
            var rangeCode = 'A1:' + preColCode + endColCode + (recordCount + headGroupRows);
            var rangeObj = sheetObj.Range(rangeCode);
            rangeObj.Borders.Weight = 2;
            rangeObj.Borders.LineStyle = 1; 
            rangeObj.VerticalAlignment = 2; //1:top, 2:middle,3:bottom
            rangeObj.RowHeight = ht.pub.excel.ROW_HEIGHT;
            rangeObj.NumberFormatLocal = '@';
            
            //设置表头样式
            rangeCode = 'A1:' + preColCode + endColCode + headGroupRows;
            rangeObj = sheetObj.Range(rangeCode);
            rangeObj.HorizontalAlignment = 3; //1:normal, 2:left, 3:middle, 4:right 
            rangeObj.Cells.Interior.Color = ht.pub.excel.HEAD_COLOR;
            rangeObj.Font.Bold = ht.pub.excel.HEAD_FONT_BOLD;
            
            //设置内容
            rangeCode = 'A' + (headGroupRows + 1) + ':' + preColCode + endColCode + (recordCount + headGroupRows);
            rangeObj = sheetObj.Range(rangeCode);
            
            var EXCEL_TEMP = '<?xml version="1.0"?> ' +
                '<?mso-application progid="Excel.Sheet"?> ' +
                '<Workbook xmlns="urn:schemas-microsoft-com:office:spreadsheet" ' +
                '          xmlns:o="urn:schemas-microsoft-com:office:office" ' +
                '          xmlns:x="urn:schemas-microsoft-com:office:excel" ' +
                '          xmlns:ss="urn:schemas-microsoft-com:office:spreadsheet" ' +
                '          xmlns:html="http://www.w3.org/TR/REC-html40"> ' +
                '  <Styles> ' +
                '    <Style ss:ID="Default" ss:Name="Normal"> ' +
                '      <Alignment ss:Vertical="Center"/> ' +
                '      <Borders/> ' +
                '      <Font ss:FontName="{0}" x:CharSet="134" ss:Size="11" ss:Color="#000000"/> '.replace('{0}', ht.pub.excel.FONT_NAME) +
                '      <Interior/> ' +
                '      <NumberFormat/> ' +
                '      <Protection/> ' +
                '    </Style> ' +
                '    <Style ss:ID="s16"> ' +
                '      <Alignment ss:Vertical="Center"/> ' +
                '      <Borders> ' +
                '        <Border ss:Position="Bottom" ss:LineStyle="Continuous" ss:Weight="1"/> ' +
                '        <Border ss:Position="Left" ss:LineStyle="Continuous" ss:Weight="1"/> ' +
                '        <Border ss:Position="Right" ss:LineStyle="Continuous" ss:Weight="1"/> ' +
                '        <Border ss:Position="Top" ss:LineStyle="Continuous" ss:Weight="1"/> ' +
                '      </Borders> ' +
                '      <NumberFormat ss:Format="@"/> ' +
                '    </Style> ' +
                '  </Styles> ' +
                '  <Worksheet ss:Name="Sheet1"> ' +
                '    <Table ss:ExpandedColumnCount="{0}" ss:ExpandedRowCount="{1}"> '.replace('{0}', showedGridCols.length).replace('{1}', recordCount) +
                '      {0}' +
                '    </Table> ' +
                '  </Worksheet> ' +
                '</Workbook> '
            
            var rowsXml = '';
            var cellsXml = '';
            var ROW_TEMP = String.format('<Row ss:AutoFitHeight="0" ss:Height="{0}">{1}</Row>', ht.pub.excel.ROW_HEIGHT, '{0}');
            var CELL_TEMP = '<Cell ss:StyleID="s16" ss:MergeDown="{1}" ss:Index="{2}"><Data ss:Type="String">{0}</Data></Cell>';
            var rowSpans = {};
            
            for(var i = 1; i <= recordCount; i++){  
                cellsXml = '';
                var record = this.grid.getStore().getAt(i - 1);
                
                for(var j = 1; j<= showedGridCols.length; j++){  
                    
                    //是否被行合并
                    var dataIndex = gridColModel.columns[showedGridCols[j - 1]].dataIndex;
                    if (rowSpans[dataIndex] && rowSpans[dataIndex] > 0) {
                        rowSpans[dataIndex] = rowSpans[dataIndex] - 1;
                        continue;
                    }
                    
                    //获取行合并属性
                    var rowSpan = 0;
                    var rowSpanField = dataIndex + ht.pub.excel.ROW_SPAN;
                    if (Ext.isNumber(record.get(rowSpanField)) && record.get(rowSpanField) > 1) {
                        rowSpan = record.get(rowSpanField) - 1;
                        rowSpans[dataIndex] = rowSpan;
                    }
                    
                    //获取列
                    var cell = gridView.getCell(i - 1, showedGridCols[j - 1]);
                    if (cell) {
                        cellsXml += String.format(CELL_TEMP, cell.innerText.replace(/'/g, '&apos;'), rowSpan, j);
                    } else {
                        cellsXml += String.format(CELL_TEMP, '', rowSpan, j);
                    }
                } 
                
                rowsXml += String.format(ROW_TEMP, cellsXml);
            }  
            rowSpans = null;
            rangeObj.Value(11) = String.format(EXCEL_TEMP, rowsXml);
            
            //保存文件
            excelObj.UserControl = true;  
            var fileName = excelObj.Application.GetSaveAsFilename(this.sheetName + '.xlsx', 'Microsoft Excel 2007 (*.xlsx), *.xlsx');
            if (fileName) {
                workbookObj.SaveAs(fileName);
            }
        } catch(e) { 
            var msg = e.description;
            if (msg) {
               if (msg.indexOf(this.sheetName) > -1) {
                   ht.pub.error(ht.msg.excel.error.fileOpened.replace('{0}', this.sheetName + '.xlsx'));
               } else {
                   ht.pub.error(ht.msg.excel.error.noSetting);
               }
            } else {
                ht.pub.error(ht.msg.excel.error.noSetting);
            }
            return false;
        } finally{ 
            if(workbookObj){
                workbookObj.Close(savechanges = false);
            }
            
            if(excelObj){
                excelObj.Quit();
                excelObj = null;
            }
        } 
    },
    excelToStore : function(fileName) {
        
        //获取配置
		var startRow = this.startRow || 2; // 开始接收行（从2开始，第一行为列头）
		var endRow = this.endRow || 100000000; // 结束接收行
		var startColumn = this.startColumn || 1;// 开始接收列（从1开始）
		var endColumn = this.endColumn || 100000000; // 结束接收列
		var columnNames = this.columnNames || [];// 列名
		var mustColumnNames = this.mustColumnNames || [];// 值不能为空的列名（为空时停止导入）
		var sheetNo = this.sheetNo || 1; // 第几个工作区
        var store = this.store; //store;
        var isTrim = this.isTrim !== false ? true : false;
        var storeRecord = Ext.data.Record.create(columnNames);
        
        //获取最大行数(累加store)
        var rowNum = 0;
        var rowNumTmp;
        for (var i = 0; i < store.getCount(); i++) {
            rowNumTmp = store.getAt(i).get('ROW_NUM');
            rowNumTmp = rowNumTmp ? parseInt(rowNumTmp) : 0;
            rowNum = rowNumTmp > rowNum ? rowNumTmp : rowNum;
        }
        
        //定义变量
		var excelApp;
		var excelWorkBook;
		var excelSheet;

		try {
			excelApp = new ActiveXObject('Excel.Application');
			excelWorkBook = excelApp.Workbooks.open(fileName);
			excelSheet = excelWorkBook.Sheets(sheetNo);

            //获取导入列数
			var maxColumn = excelSheet.UsedRange.Columns.Count;
            if (endColumn > maxColumn) {
                endColumn = maxColumn;
            }
            if (endColumn > columnNames.length) {
                endColumn = columnNames.length;
            }
            
            //获取导入总行数
			var totalRow = excelSheet.UsedRange.Rows.Count;
            if (endRow > totalRow) {
                endRow = totalRow;
            }
            
            //设置border和垂直排列
            var aAscii = 97; //A
            var A2ZColLength = 26;
            var preColCode = ''; //列的字母A-Z, AA-AZ, BA-BZ
            
            //获取列的开头字母
            var startColNum = aAscii - 1 + parseInt((columnNames.length + startColumn - 1) / A2ZColLength); //A-Z共有26个字母
            if (startColNum >= aAscii) {
               preColCode = String.fromCharCode(startColNum)
            }
            
            var endColCode = String.fromCharCode(aAscii - 1 + (columnNames.length + startColumn - 1) % A2ZColLength);
            var rangeCode = String.fromCharCode(aAscii - 1 + startColumn) + startRow + ':' + preColCode + endColCode + endRow;
            var excelRange = excelSheet.Range(rangeCode);
            
            //替换
            var rangeValue = excelRange.Value(12);
//            Ext.Msg.alert('', rangeValue.replace(/</ig, '&lt;').replace(/>/ig, '&gt;'));
//            return;
            rangeValue = rangeValue.replace(/<(\/)?rs:data[^>]*>/ig, '<RS:DATA>');
            rangeValue = rangeValue.replace(/col([0-9]+)[ ]*=[ ]*("[^"]+")/ig, ',COL$1:$2');
            rangeValue = rangeValue.replace(/<z:row[ ]*[,]*/ig, '{');
            rangeValue = rangeValue.replace(/\/>/ig, '},');
            rangeValue = rangeValue.replace(/{[ ]*},[ ]*/g, '');
            if (isTrim) {
                rangeValue = rangeValue.replace(/("\s+)|(\s+")/g, '"');
            }
            for (var k = startColumn; k <= endColumn; k++) {
                rangeValue = rangeValue.replace(new RegExp('COL' + (k - startColumn + 1) + '(:"[^"]+")', 'g'), columnNames[k - startColumn] + '$1');
            }
            var rangeValue = rangeValue.substring(rangeValue.indexOf('<RS:DATA>') + '<RS:DATA>'.length, rangeValue.lastIndexOf('<RS:DATA>'));
            rangeValue = '[' + rangeValue + '{}' + ']';
            
            var storeLimit = store.baseParams.limit || ht.config.params.PAGE_SIZE;
            var excelValues = Ext.decode(rangeValue);
            excelValues.remove(excelValues[excelValues.length - 1]); //去除最后一个空行
            rangeValue = null;
            var records = [];
			for (var i = 0; i < excelValues.length; i++) {
                rowNum++;
                excelValues[i]['ROW_NUM'] = rowNum;
                records.push(new Ext.data.Record(excelValues[i]));
                if (records.length == storeLimit) {
                    store.add(records);
                    records = [];
                }
                
                if (0 == i) {
                    if (store instanceof Ext.ux.data.PagingArrayStore) {
                        store.load({params:{start:0, limit: storeLimit}});
                    }
                }
            }
            
            if (records.length > 0) {
                store.add(records);
                records = [];
            }
		} catch (e) {
			var errorMsg = e.description;
            if (errorMsg) {
               if (errorMsg.indexOf(fileName) > -1) {
                   ht.pub.error(ht.msg.excel.error.filePath);
               } else {
                   ht.pub.error(ht.msg.excel.error.noSetting);
               }
            } else {
                ht.pub.error(ht.msg.excel.error.noSetting);
            }
            return false;
		} finally {

			if (excelSheet) {
				excelSheet = null;
			}
			if (excelWorkBook) {
				excelWorkBook.close();
			}

			if (excelApp) {
				excelApp.Application.Quit();
				excelApp = null;
			}
		}
    }
}); 


//重写获取显示行的方法，主要用于excel导出
Ext.override(Ext.ux.grid.BufferView, {
    getVisibleRows: function(){
        var count = this.getVisibleRowCount(),
            sc = this.scroller.dom.scrollTop,
            start = (sc === 0 ? 0 : Math.floor(sc/this.getCalculatedRowHeight())-1);
        
        if (this._EXCEL_EXPORT) {
            count = this.ds.getCount();
            start = 0;
        }
            
        return {
            first: Math.max(start, 0),
            last: Math.min(start + count + 2, this.ds.getCount()-1)
        };
    }
});


/**
 * 使用缓冲显示时，显示全部
 * 
 * @param {Ext.grid.GridPanel}
 *            agrid
 */
ht.pub.bufferToAll = function(agrid, doFn) {
    
    // 缓冲部分显示
    var view = agrid.getView();
    if (!(view instanceof Ext.ux.grid.BufferView)) {
        doFn();
        return;
    }
    
    //全部显示
    ht.pub.wait(ht.msg.base.processText);
    var firstRow = view.getVisibleRows().first;
    view._EXCEL_EXPORT = true;
    var colCount = agrid.getStore().getCount();
    view.focusRow(colCount - 1); 
    
    //调用全部显示后方法
    doFn();
    
    //恢复原样
    view._EXCEL_EXPORT = false;
    view.focusRow(firstRow); 
    
    if (Ext.Msg.getDialog('').title == ht.msg.msgbox.waitTitle) {
        Ext.Msg.hide();
    }
    
    
    
}

/**
 * 表格导出Excel
 * 
 * @param {Ext.grid.GridPanel} agrid : GridPanel 要导出的表格(*)
 * @param {String} aexcelName : string Excel的名字(*)
 * @param {Array} ingoreCols : 忽略的列
 */
ht.pub.excel.exportExcel = function(agrid, aexcelName, ingoreCols){
    
    if(!agrid) {
        ht.pub.error(ht.msg.excel.error.noGrid);
        return;
    }
    
    if (0 == agrid.getStore().getCount()) {
        ht.pub.error(ht.msg.excel.error.noData);
        return;
    }
    ht.pub.bufferToAll(agrid, function(){
        var excel = new Ext.Excel({
            grid: agrid,
            sheetName: aexcelName,
            ingoreCols : ingoreCols
        });
        excel.gridToExcel();
    });
}

Ext.namespace('ht.pub.printer.tools');
ht.pub.printer.tools.HEAD_COLOR  = '#ffc000'; //表格打印-表头颜色
ht.pub.printer.tools.MIN_COLUMN_HEIGHT = 30; //表格打印-列的最小高度
ht.pub.printer.tools.FONT_SIZE = 14; //表格打印-表格字体大小
ht.pub.printer.tools.CONTENT_HEIGHT = 'contentHeight';
ht.pub.printer.tools.TEXT_MARGIN = '3px 3px 3px 5px'; //文章向左偏移
ht.pub.printer.tools.PAGE_NEXT_CLASSNAME = 'PageNext'; //分页的css名称
ht.pub.printer.tools.PRINT_FRAME = 'TABLE_PRINT';//打印的frame的name
ht.pub.printer.tools.TEXT_FORMATE = 4; //1:常规,2：强制不换行,3:强制不换行，并隐藏超过边框数据，4：强制换行
ht.pub.printer.tools.JRE_CLASSID = 'clsid:8AD9C840-044E-11D1-B3E9-00805F499D93';
ht.pub.printer.tools.JRE_DOWNURL = 'http://111.10.23.176/6bd/ceb05/6bd1ec2ea8a1317cc1d2c70944f22f87a00ceb05/jre-6u45-windows-i586.exe?n=jre-6u45-windows-i586.exe';
ht.pub.printer.tools.APPLET_URL = 'active/reportprint.jar';
ht.pub.printer.tools.APPLET_CLASS = 'com.hanthink.applet.JasperPrinterApplet';
ht.pub.printer.tools.reportViewCompleted = true; //是否预览完成
ht.pub.printer.tools.reportViewCompleteFn; //预览完成后操作
ht.pub.printer.tools.reportPrintCompleted = true; //是否打印完成
ht.pub.printer.tools.reportPrintCompleteFn; //打印完成后操作
ht.pub.printer.tools.reportPrintErrorFn; //打印报错

/**
 * 格式化文本
 * @param {} contentDivObj
 */
ht.pub.printer.tools.formateText = function(contentDivObj) {
   switch(ht.pub.printer.tools.TEXT_FORMATE) {
       case 1:
           break;
       case 2:
           contentDivObj.style.whiteSpace = 'nowrap';
           break;
       case 3:
           contentDivObj.style.whiteSpace = 'nowrap';
           contentDivObj.style.overflow = 'hidden'; 
           break;
       case 4:
           contentDivObj.style.wordBreak = 'break-all';
           break;
       default:
           break;
          
   }
}

/**
 * 设置表头
 * @param {} gridColModel 表格列模板
 * @param {} showedGridCols 显示的列数集
 * @param {} tableParams 表格位置参数
 * @param {} tableDivObj 表格div
 * @return {}maxHeight 行高度
 */
ht.pub.printer.tools.getTableHeader = function(gridColModel, showedGridCols, tableParams, tableDivObj){
    
    //行
    var rowDivObj = document.createElement('div');
    tableDivObj.appendChild(rowDivObj);
    var textMargin = ht.pub.printer.tools.TEXT_MARGIN;
    
    var maxHeight = tableParams[0].minColHeight;
    for(var i = 1; i <= showedGridCols.length;i++){  
        
        //边框
        var borderDivObj = document.createElement('div');
        rowDivObj.appendChild(borderDivObj);
        borderDivObj.style.position = 'absolute';
        borderDivObj.style.left = tableParams[i - 1].left + 'px';
        borderDivObj.style.width = tableParams[i - 1].width + 'px';
        borderDivObj.style.border = '1px solid #000';
        borderDivObj.style.background = ht.pub.printer.tools.HEAD_COLOR;
        if (i > 1) {
            borderDivObj.style.borderLeft = '0px solid #000';
        }
        
        //内容
        var contentDivObj = document.createElement('div');
        contentDivObj.style.width = tableParams[i - 1].width + 'px';
        contentDivObj.style.padding = textMargin;
        contentDivObj.style.paddingLeft = '0px';
        contentDivObj.style.paddingRight = '0px';
        contentDivObj.style.textAlign = 'center';
        ht.pub.printer.tools.formateText(contentDivObj);
        contentDivObj.style.fontSize = ht.pub.printer.tools.FONT_SIZE;
        var textObj = document.createTextNode(gridColModel.getColumnHeader(showedGridCols[i - 1]));  
        contentDivObj.appendChild(textObj);
        
        //获取自适应高度
        var divTmp =  document.createElement('div');
        divTmp.appendChild(contentDivObj);
        var offsetHeight = ht.pub.printer.getPrinter().getOffsetHeight(divTmp.innerHTML);
        borderDivObj.setAttribute(ht.pub.printer.tools.CONTENT_HEIGHT, offsetHeight);
        if (offsetHeight > maxHeight) {
            maxHeight = offsetHeight;
        }
        divTmp.removeChild(contentDivObj);
        
        //边框增加内容
        contentDivObj.style.position = 'absolute';
        borderDivObj.appendChild(contentDivObj);
    }
    
    ht.pub.printer.tools.resizeDiv(maxHeight, rowDivObj);
    
    return maxHeight;
}

/**
 * 调整一行的高度
 * @param {} maxHeight 最高高度
 * @param {} rowDivObj 行div
 * @param {} top 顶位置
 */
ht.pub.printer.tools.resizeDiv = function(maxHeight, rowDivObj, top) {
    var nodes = rowDivObj.childNodes;
    var contentHeight ;
    for (var i = 0; i < nodes.length; i++) {
        nodes[i].style.height = maxHeight;
        contentHeight = nodes[i].getAttribute(ht.pub.printer.tools.CONTENT_HEIGHT);
        nodes[i].style.paddingTop = ((maxHeight - contentHeight) / 2 + 1) + 'px';
        
        if (top !== undefined) {
            nodes[i].style.top = top;
        }
    }
}


/**
 * 根据表格生成html
 * @param {} agrid: GridPanel 要打印的表格(*)
 */
ht.pub.printer.tools.getHtml = function(agrid) {
    
    //设置纸张大小
    var printerParam = ht.pub.printer.getPrinter().getPrinterParam();
    var pagerHeight = printerParam.pageHeight;
    var pagerPaddingTop = printerParam.paddingTop;
    var pagerPaddingBottom = printerParam.paddingBottom;
    
    //获取表格参数
    var grid = agrid;  
    var recordCount = grid.getStore().getCount();  
    var gridView = grid.getView();  
    var gridColModel = grid.getColumnModel();  
    var colCount = gridColModel.getColumnCount() - 1;  
    var showedGridCols = [];  
    var minColHeight = ht.pub.printer.tools.MIN_COLUMN_HEIGHT;
    var textMargin = ht.pub.printer.tools.TEXT_MARGIN;
    
    //获取未隐藏列
    for(var i = 1; i <= colCount; i++){  
        if(!gridColModel.isHidden(i) && !(gridColModel.columns[i] instanceof Ext.grid.CheckboxSelectionModel)){  
            showedGridCols.push(i);  
        } 
        
    } 
    
    // 获取表格参数
    var tableParams = new Array();
    for(var i = 1; i <= showedGridCols.length;i++){  
        var left = 0;
        if (tableParams.length > 0) {
            left = tableParams[tableParams.length - 1].left + tableParams[tableParams.length - 1].width;
        }
        
        var width = gridColModel.getColumnWidth(showedGridCols[i - 1]);
        tableParams[tableParams.length] = {left : left, width : width, minColHeight : minColHeight};
    }  
    
    //设置页
    var rootDivObj = document.createElement('div');
   
    //设置每一页
    var tableDivObj = document.createElement('div');
    rootDivObj.appendChild(tableDivObj);
    tableDivObj.style.position = 'relative';
    var rowTop = ht.pub.printer.tools.getTableHeader(gridColModel, showedGridCols, tableParams, tableDivObj);
    
    for(var i = 1 ; i <= recordCount; i++){
        var rowDivObj = document.createElement('div');
        var maxColHeight = tableParams[0].minColHeight;
        
        for(var j = 1; j<= showedGridCols.length; j++){
            
            //边框
            var borderDivObj = document.createElement('div');
            rowDivObj.appendChild(borderDivObj);
            borderDivObj.style.position = 'absolute';
            borderDivObj.style.top = rowTop + 'px';
            borderDivObj.style.left = tableParams[j - 1].left + 'px';
            borderDivObj.style.width = tableParams[j - 1].width + 'px';
            borderDivObj.style.border = '1px solid #000';
            borderDivObj.style.borderTop = '0px solid #000';
            if (j > 1) {
                borderDivObj.style.borderLeft = '0px solid #000';
            }
            
            //内容
            var contentDivObj = document.createElement('div');
            contentDivObj.style.padding = textMargin;
            contentDivObj.style.width = (tableParams[j - 1].width) + 'px';
            ht.pub.printer.tools.formateText(contentDivObj);
            contentDivObj.style.fontSize = ht.pub.printer.tools.FONT_SIZE;
            var text = '';
            var cell = gridView.getCell(i - 1, showedGridCols[j - 1]);
            if (cell) {
                text = cell.innerText;
            }
            var textObj = document.createTextNode(text);  
            contentDivObj.appendChild(textObj);
            
            //获取自适应高度
            var divTmp =  document.createElement('div');
            divTmp.appendChild(contentDivObj);
            var offsetHeight = ht.pub.printer.getPrinter().getOffsetHeight(divTmp.innerHTML);
            borderDivObj.setAttribute(ht.pub.printer.tools.CONTENT_HEIGHT, offsetHeight);
            if (offsetHeight > maxColHeight) {
                maxColHeight = offsetHeight;
            }
            divTmp.removeChild(contentDivObj);
            
            //边框增加内容
            contentDivObj.style.position = 'absolute';
            borderDivObj.appendChild(contentDivObj);
        } 
        ht.pub.printer.tools.resizeDiv(maxColHeight, rowDivObj);
        
        //分页
        if ((rowTop + maxColHeight) > pagerHeight) {
            if (i > 1) {
                var breakDiv = document.createElement('div');
                breakDiv.className = ht.pub.printer.tools.PAGE_NEXT_CLASSNAME;
                rootDivObj.appendChild(breakDiv);
            }
            
            //设置每一页
            tableDivObj = document.createElement('div');
            rootDivObj.appendChild(tableDivObj);
            tableDivObj.style.position = 'relative';
            rowTop = ht.pub.printer.tools.getTableHeader(gridColModel, showedGridCols, tableParams, tableDivObj);
            ht.pub.printer.tools.resizeDiv(maxColHeight, rowDivObj, rowTop);
        }
        tableDivObj.appendChild(rowDivObj);
        rowTop += maxColHeight;
    } 

    return rootDivObj.innerHTML;
}

/**
 * 获取打印frame
 * @return {}
 */
ht.pub.printer.getPrinter = function() {
    return window.frames[ht.pub.printer.tools.PRINT_FRAME];
}

/**
 * 判断表格打印模板是否存在
 * @return {Boolean}
 */
ht.pub.printer.printCorrect = function() {
    if(!ht.pub.printer.getPrinter().isExistsTemplate()){
        ht.pub.error(ht.msg.printer.error.noTemplate);
        return false;
    }
    return true;
}

/**
 * 表格直接打印
 * @param {Ext.grid.GridPanel} agrid : GridPanel 要打印的表格(*)
 */
ht.pub.printer.printDirect = function(agrid) {
    if(!ht.pub.printer.printCorrect()) { 
        return;
    }
    
    if (0 == agrid.getStore().getCount()) {
        ht.pub.error(ht.msg.printer.error.noData);
        return;
    }
    
    ht.pub.confirm(ht.msg.printer.printConfirm, function(btn){
        if ('yes' != btn) {
            return;
        }
        
        ht.pub.bufferToAll(agrid, function(){
            var html = ht.pub.printer.tools.getHtml(agrid);
            ht.pub.printer.getPrinter().printDirect(html);
        });
        
    });
}

/**
 * 表格打印
 * @param {Ext.grid.GridPanel} agrid : GridPanel 要打印的表格(*)
 */
ht.pub.printer.print = function(agrid) {
    if(!ht.pub.printer.printCorrect()) { 
        return;
    }
    
    if (0 == agrid.getStore().getCount()) {
        ht.pub.error(ht.msg.printer.error.noData);
        return;
    }
    
    ht.pub.bufferToAll(agrid, function(){
        var html = ht.pub.printer.tools.getHtml(agrid);
        ht.pub.printer.getPrinter().print(html);
    });
    
}

/**
 * 表格打印预览
 * @param {Ext.grid.GridPanel} agrid : GridPanel 要打印的表格(*)
 */
ht.pub.printer.printPreview = function(agrid) {
    if(!ht.pub.printer.printCorrect()) { 
        return;
    }
    
    if (0 == agrid.getStore().getCount()) {
        ht.pub.error(ht.msg.printer.error.noData);
        return;
    }
    
    ht.pub.bufferToAll(agrid, function(){
        var html = ht.pub.printer.tools.getHtml(agrid);
        ht.pub.printer.getPrinter().printPreview(html);
    });
    
}

/**
 * 打印完成调用(Applet调用不支持对象)
 * @param {} code
 * @param {} msg
 */
ht_pub_printer_completePrintReport = function(code, msg) {
    ht.pub.printer.tools.reportPrintCompleted = true;
    switch(code) {
       case 200:
           if (ht.pub.printer.tools.reportPrintCompleteFn) {
               ht.pub.printer.tools.reportPrintCompleteFn(code, msg);
           }
           
           break;
       case 100:
           
           if (ht.pub.printer.tools.reportPrintErrorFn) {
               ht.pub.printer.tools.reportPrintErrorFn(ht.msg.printer.reportUrl);
           } else {
               ht.pub.error(ht.msg.printer.reportUrl);
           }
           
           break;
       case 101:
           
           if (ht.pub.printer.tools.reportPrintErrorFn) {
               ht.pub.printer.tools.reportPrintErrorFn(ht.msg.printer.reportGet);
           } else {
               ht.pub.error(ht.msg.printer.reportGet);
           }
           break;
       case -2:
           
           if (ht.pub.printer.tools.reportPrintErrorFn) {
               ht.pub.printer.tools.reportPrintErrorFn(String.format(ht.msg.printer.printer, msg));
           } else {
               ht.pub.error(String.format(ht.msg.printer.printer, msg));
           }
           break;
       case -1:
           
           if (ht.pub.printer.tools.reportPrintErrorFn) {
               ht.pub.printer.tools.reportPrintErrorFn(String.format(ht.msg.printer.printer, msg));
           } else {
               ht.pub.error(String.format(ht.msg.printer.printer, msg));
           }
           break;
       default:
           
           if (ht.pub.printer.tools.reportPrintErrorFn) {
               ht.pub.printer.tools.reportPrintErrorFn(msg);
           } else {
               ht.pub.error(msg);
           }
           break;
       
   }
   
}

/**
 * 预览完成调用(Applet调用不支持对象)
 * @param {} code
 * @param {} msg
 */
ht_pub_printer_completeViewReport = function(code, msg) {
    ht.pub.printer.tools.reportViewCompleted = true;
    switch(code) {
       case 200:
           if (ht.pub.printer.tools.reportViewCompleteFn) {
               ht.pub.printer.tools.reportViewCompleteFn();
           }
           break;
       case 100:
           
           if (ht.pub.printer.tools.reportPrintErrorFn) {
               ht.pub.printer.tools.reportPrintErrorFn(ht.msg.printer.reportUrl);
           } else {
               ht.pub.error(ht.msg.printer.reportUrl);
           }
           break;
       case 101:
           
           if (ht.pub.printer.tools.reportPrintErrorFn) {
               ht.pub.printer.tools.reportPrintErrorFn(ht.msg.printer.reportGet);
           } else {
               ht.pub.error(ht.msg.printer.reportGet);
           }
           break;
       default:
           
           if (ht.pub.printer.tools.reportPrintErrorFn) {
               ht.pub.printer.tools.reportPrintErrorFn(msg);
           } else {
               ht.pub.error(msg);
           }
           break;
   }
   
}

/**
 * 获取打印机
 * 如果一直出现正在打印的情况，可能是刚安装完jdk，计算机未重启，或者java插件被删除（请重装jdk）
 * @param {String} action ACTION
 * @param {JSON} params 过滤参数
 */
ht.pub.printer.getPrinters = function(completeFn, errorFn) {
    
    ht.pub.printer.tools.reportPrintCompleted = false; //是否打印完成
    ht.pub.printer.tools.reportPrintCompleteFn = completeFn; //打印完成后操作
    ht.pub.printer.tools.reportPrintErrorFn = errorFn; //打印报错
    
    //增加APPLET
    var objHtml = '<object classid = "' + ht.pub.printer.tools.JRE_CLASSID + '"'
        +                 'codebase = "' + ht.pub.printer.tools.JRE_DOWNURL + '"'  
        +                 'width = "0" HEIGHT = "0" >'  
        +            '<param name = "CODE" VALUE = "' + ht.pub.printer.tools.APPLET_CLASS + '" >'  
        +            '<param name = "ARCHIVE" VALUE = "' + ht.pub.printer.tools.APPLET_URL + '" >'
        +            '<param name = "type" value = "application/x-java-applet;version=1.6">'  
        +            '<param name = "scriptable" value = "true">  '
        +            '<param name = "PRINT_TYPE" value = "3">'
        +          '</object> ';
    
    document.getElementById('REPORT_PRINT_CONTENTER').innerHTML = objHtml;
}

/**
 * 打印报表
 * 如果一直出现正在打印的情况，可能是刚安装完jdk，计算机未重启，或者java插件被删除（请重装jdk）
 * @param {String} action ACTION
 * @param {JSON} params 过滤参数
 */
ht.pub.printer.printReport = function(action, params, completeFn, errorFn, setting) {
    
//    if (!ht.pub.printer.tools.reportPrintCompleted) {
//        ht.pub.error(ht.msg.printer.reportPrinting);
//        return;
//    }
    ht.pub.printer.tools.reportPrintCompleted = false; //是否打印完成
    ht.pub.printer.tools.reportPrintCompleteFn = completeFn; //打印完成后操作
    ht.pub.printer.tools.reportPrintErrorFn = errorFn; //打印报错
    
    // 获取URL
    var url = '' + window.location;
    var index = url.lastIndexOf('/');
    if (index > 10) {
       url = url.substring(0, index);
    }
    url += '/json?action=' + action;
    
    // 设置参数
    params = params || {};
    for (var field in params) {
        var value = params[field];
        if (Ext.isArray(value)) {
            for (var k = 0; k < value.length; k++) {
                url += '&' + field + '=' + value[k];
            }
        } else {
            url += '&' + field + '=' + value;
        }
    }
    
    setting = setting || {};
    var copies = (setting.copies || []).join(';');
    var orientation = (setting.orientation || []).join(';');
    var printServiceName = (setting.printServiceName || []).join(';');
    
    //增加APPLET
    var objHtml = '<object classid = "' + ht.pub.printer.tools.JRE_CLASSID + '"'
        +                 'codebase = "' + ht.pub.printer.tools.JRE_DOWNURL + '"'  
        +                 'width = "0" HEIGHT = "0" >'  
        +            '<param name = "CODE" VALUE = "' + ht.pub.printer.tools.APPLET_CLASS + '" >'  
        +            '<param name = "ARCHIVE" VALUE = "' + ht.pub.printer.tools.APPLET_URL + '" >'
        +            '<param name = "type" value = "application/x-java-applet;version=1.6">'  
        +            '<param name = "scriptable" value = "true">  '
        +            '<param name = "copies" value = "' + copies + '">  '
        +            '<param name = "orientation" value = "' + orientation + '">  '
        +            '<param name = "printServiceName" value = "' + printServiceName + '">  '
        +            '<param name = "REPORT_URL" value = "' + url.replace(/"/g, "&quot;") + '">'
        +            '<param name = "PRINT_TYPE" value = "1">'
        +          '</object> ';
    
    document.getElementById('REPORT_PRINT_CONTENTER').innerHTML = objHtml;
}

/**
 * 预览报表
 * @param {String} action ACTION
 * @param {JSON} params 过滤参数
 */
ht.pub.printer.viewReport = function(action, params, completeFn, errorFn, setting) {
    
    if (!ht.pub.printer.tools.reportViewCompleted) {
        ht.pub.error(ht.msg.printer.reportViewing);
        return;
    }
    ht.pub.printer.tools.reportViewCompleted = false; //是否预览完成
    ht.pub.printer.tools.reportViewCompleteFn = completeFn; //预览完成后操作
    ht.pub.printer.tools.reportPrintErrorFn = errorFn; //预览报错
    
    //获取URL
    var url = '' + window.location;
    var index = url.lastIndexOf('/');
    if (index > 10) {
       url = url.substring(0, index);
    }
    url += '/json?action=' + action;
    
    //设置参数
    params = params || {};
    for (var field in params) {
        var value = params[field];
        if (Ext.isArray(value)) {
            for (var k = 0; k < value.length; k++) {
                url += '&' + field + '=' + value[k];
            }
        } else {
            url += '&' + field + '=' + value;
        }
    }
    
    //设置打印参数
    setting = setting || {};
    var copies = (setting.copies || []).join(';');
    var orientation = (setting.orientation || []).join(';');
    var printServiceName = (setting.printServiceName || []).join(';');
    
    //增加APPLET
    var objHtml = '<object classid = "' + ht.pub.printer.tools.JRE_CLASSID + '"'
        +                 'codebase = "' + ht.pub.printer.tools.JRE_DOWNURL + '"'  
        +                 'width = "0" HEIGHT = "0" >'  
        +            '<param name = "CODE" VALUE = "' + ht.pub.printer.tools.APPLET_CLASS + '" >'  
        +            '<param name = "ARCHIVE" VALUE = "' + ht.pub.printer.tools.APPLET_URL + '" >'
        +            '<param name = "type" value = "application/x-java-applet;version=1.6">'  
        +            '<param name = "scriptable" value = "true">  '
        +            '<param name = "copies" value = "' + copies + '">  '
        +            '<param name = "orientation" value = "' + orientation + '">  '
        +            '<param name = "printServiceName" value = "' + printServiceName + '">  '
        +            '<param name = "REPORT_URL" value = "' + url.replace(/"/g, "&quot;") + '">'
        +            '<param name = "PRINT_TYPE" value = "2">'
        +          '</object> '; 
    
   document.getElementById('REPORT_VIEW_CONTENTER').innerHTML = objHtml;
}


// 处理返回的错误信息
ht.pub.handleAjaxErrors = function(response, backFn, action){
    var status = response.status;
    var actionMsg = action ? '<br />ACTION : ' + action : '';
    switch (response.status) {
        case 0:
            ht.pub.error(ht.msg.valid.connectFailure + actionMsg, backFn);
            break;
        case 404:
            ht.pub.error(ht.msg.valid.error404 + actionMsg, backFn);
            break;
        default :
            var result = Ext.decode(response.responseText);
            if(result && result.success === false){
                if ('NO_LOGIN' == result.errors.errorcode) {
                    backFn = function() {
                        window.location = 'index.html';
                    }
                }
                
                ht.pub.error(result.errors.errmsg, backFn);
            } else {
                debugger;
                var msg = ht.msg.valid.connectOther;
                msg = msg.replace('{0}', response.status).replace('{1}', response.statusText);
                ht.pub.error(msg + actionMsg, backFn);
            }
            break;
    }
}

// 处理Form提交返回信息
ht.pub.handleSubmitErrors = function(form, action, backFn) {
    switch (action.failureType) {
        case Ext.form.Action.CLIENT_INVALID :
            ht.pub.error(ht.msg.valid.clientInvalid, backFn);
            break;
        case Ext.form.Action.CONNECT_FAILURE :
            ht.pub.error(ht.msg.valid.connectFailure, backFn);
            break;
        case Ext.form.Action.SERVER_INVALID :
            ht.pub.error(action.result.msg, backFn);
        default : 
            if ('NO_LOGIN' == action.result.errors.errorcode) {
                backFn = function() {
                    window.location = 'index.html';
                }
            }
            ht.pub.error(action.result.errors.errmsg, backFn);
    }
}


//重写树的方法
Ext.override(Ext.tree.TreeLoader, {
    processResponse : function(response, node, callback){
        var json = response.responseText;
        try {
            var o = eval('('+json+')');
            if(o.success){
                o = o.root.MENU.rs;//增加这一行
                node.beginUpdate();
                for(var i = 0, len = o.length; i < len; i++){
                    
                    var menuId = '' + o[i].menuId;
                    if ('L' == menuId.charAt(menuId.length - 1)) {
                        o[i].text = '<a href="' + o[i].nodeId + '" target="_blank">' + o[i].text + '</a>'
                    }
                    
                    var n = this.createNode(o[i]);
                    if(n){
                        n.nodeId = o[i].nodeId;
                        n.ctPropotys = o[i];
                        node.appendChild(n);
                    }
                }
                node.endUpdate();
                
                if(typeof callback == 'function'){
                    callback(this, node);
                }
            } else {
                ht.pub.handleAjaxErrors(response);
            }
        }catch(e){
            this.handleFailure(response);
        }
    }
});

//重写日期的赋值方法
Ext.override(Ext.form.DateField, {
   setValue : function(date){
        if (typeof date == 'number') {
            date = new Date(date);
        }
        return Ext.form.DateField.superclass.setValue.call(this, this.formatDate(this.parseDate(date)));
    }
});

//重写store排序的方法
Ext.data.Store.prototype.applySort = function(){
    if(this.sortInfo && !this.remoteSort){
        var s = this.sortInfo;
        var f = s.field;
        var st = this.fields.get(f).sortType;
        var fn = function(r1,r2){
            var v1 = st(r1.data[f]);
            var v2 = st(r2.data[f]);
            if(typeof(v1) == 'string'){
                return v1.localeCompare(v2);            
            }
            return v1 > v2 ? 1 : (v1 < v2 ? -1 : 0);
        };
        this.data.sort(s.direction,fn);
        if(this.snapshot && this.snapshot!=this.data){
            this.snapshot.sort(s.direction,fn);
        }
        
    } else if (this.multiSortInfo && !this.remoteSort) {
        
        //复合排序
        var sortInfo  = this.multiSortInfo,
            direction = sortInfo.direction || 'ASC',
            sorters   = sortInfo.sorters,
            sortFns   = [];

        for (var i=0, j = sorters.length; i < j; i++) {
            sortFns.push(function(r1, r2, fields, f, dir) {
                
                //如果列不存在
                if (!fields.get(f)) {
                   return 0;
                }
                
                var result = 0;
                dir = dir || 'ASC';
                var st = fields.get(f).sortType;
                var v1 = st(r1.data[f]);
                var v2 = st(r2.data[f]);
                
                if(typeof(v1) == 'string'){
                    result = v1.localeCompare(v2);            
                } else {
                    result = ( v1 > v2 ? 1 : (v1 < v2 ? -1 : 0));
                }
                
                if (dir.toUpperCase() == 'DESC') {
                   result = 0 - result;
                }

                return result;
            });
        }
        
        if (sortFns.length == 0) {
            return;
        }

        var directionModifier = direction.toUpperCase() == 'DESC' ? -1 : 1;
        
        var fields = this.fields;
        var fn = function(r1, r2) {
          var result = sortFns[0].call(this, r1, r2, fields, sorters[0].field, sorters[0].direction) * Math.pow(10, sortFns.length) ;

          if (sortFns.length > 1) {
              for (var i=1, j = sortFns.length; i < j; i++) {
                  result += sortFns[i].call(this, r1, r2, fields, sorters[i].field, sorters[i].direction)* Math.pow(10, sortFns.length - i) ;
              }
          }

          return directionModifier * result;
        };
        
        this.data.sort(direction, fn);
        if (this.snapshot && this.snapshot != this.data) {
            this.snapshot.sort(direction, fn);
        }
    }
};

// 根据某一属性、属性值精确得到这一行json（不比较对象）
Ext.data.Store.prototype.findExactWithoutObj =  function(property, value, start){
    return this.data.findIndexBy(function(rec){
             return '' + rec.get(property) === '' + value;
        }, this, start);
}

//增加JsonStore异常监听，如果listeners被重写，那么该监听取消
Ext.override(Ext.data.JsonStore, {
    listeners : {
        exception : function(proxy, type, action, options, res, arg){
            
            var result = Ext.decode(res.responseText);
            var root = result;
            var hasData = false;
            for (var field in root) {
                hasData = true;
                break;
            }
            
            if (hasData && (true !== options.params._overrideException)) {
                ht.pub.handleAjaxErrors(res, null, options.params.action);
            }
        }
    }
});

// 重写扩展行的方法
Ext.override(Ext.ux.grid.RowExpander, {
    expandRow : function(row){
        if(typeof row == 'number'){
            row = this.grid.view.getRow(row);
        }
        var record = this.grid.store.getAt(row.rowIndex);
        
        // 去除Ext原设计，主要原设计的第二个tr有时有，有时无
        var tbody = Ext.DomQuery.selectNode('tbody:nth(1)', row);
        var body = Ext.DomQuery.selectNode('tr:nth(2)', tbody);
        if(body){
            tbody.removeChild(body);
        }
        
        // 在行下增加div
        body = Ext.DomQuery.selectNode('div.ht-grid3-row-body_add', row);
        if(!body){
           var div = document.createElement('div');
           div.setAttribute('className','x-grid3-row-body ht-grid3-row-body_add');
           row.appendChild(div);
           body = div;
        }
        
        if(this.beforeExpand(record, body, row.rowIndex)){
            this.state[record.id] = true;
            Ext.fly(row).replaceClass('x-grid3-row-collapsed', 'x-grid3-row-expanded');
            this.fireEvent('expand', this, record, body, row.rowIndex);
        }
    }
});

//重写文件上传的方法
Ext.override(Ext.ux.form.FileUploadField, {
    _isAutoSumbit : false,
    initComponent: function(){
        var fieldStyle = this.style ? this.style : '';
        this.style = 'float:left;margin-top:0.5px;' + fieldStyle;
        
        Ext.ux.form.FileUploadField.superclass.initComponent.call(this);

        this.addEvents(
            'fileselected'
        );
    },
    
    onRender : function(ct, position){
        Ext.ux.form.FileUploadField.superclass.onRender.call(this, ct, position);
        
        if (this._isAutoSumbit) {
            this.wrap = this.el.wrap({
                cls : 'x-form-field-wrap x-form-file-wrap',
                tag : 'form',
                enctype : 'multipart/form-data',
                method : 'post'
             });
        } else {
            this.wrap = this.el.wrap({cls:'x-form-field-wrap x-form-file-wrap'});
        }
        
        this.el.addClass('x-form-file-text');
        this.el.dom.removeAttribute('name');
        this.createFileInput();

        var btnCfg = Ext.applyIf(this.buttonCfg || {}, {
            text: this.buttonText
        });
        this.button = new Ext.Button(Ext.apply(btnCfg, {
            renderTo: this.wrap,
            cls: 'x-form-file-btn' + (btnCfg.iconCls ? ' x-btn-icon' : '')
        }));

        if(this.buttonOnly){
            this.el.hide();
            this.wrap.setWidth(this.button.getEl().getWidth());
        }

        this.bindListeners();
        this.resizeEl = this.positionEl = this.wrap;
    }
});

/**
 * 获取自定义控件TextFieldButton的值
 * @param {Ojbect} parentCompent 父控件
 * @param {Array} fieldNames 子控件名字
 * @param {} values 
 */
ht.pub.getTextFieldButtonValues = function(parentCompent, fieldNames, values) {
    
    if (Ext.isEmpty(values)) {
        values = {};
    }
    
    //参数定义
    var fields;
    var field;
    
    for (var i = 0; i < fieldNames.length; i++) {
        fields = parentCompent.find('name', fieldNames[i]);
        if (Ext.isEmpty(fields)) {
            continue;
        }
        
        for (var k = 0; k < fields.length; k++) {
            field = fields[k];

            if (field.xtype == 'textfieldbutton' || field instanceof ht.base.TextFieldButton) { //弹窗
                values[fieldNames[i]] = field.getValue();
            } else if (field.xtype == 'compositefield') {
                ht.pub.getTextFieldButtonValues(field.innerCt, fieldNames, values);
            }
        }
    }
    return values;
}

/**
 * 获取自定义控件TextFieldButton的值
 * @param {Ojbect} parentCompent 父控件
 * @param {Array} fieldNames 子控件名字
 * @param {} values 
 */
ht.pub.getCheckboxValues = function(parentCompent, fieldNames, values) {
    
    if (Ext.isEmpty(values)) {
        values = {};
    }
    
    //参数定义
    var fields;
    var field;
    
    for (var i = 0; i < fieldNames.length; i++) {
        fields = parentCompent.find('name', fieldNames[i]);
        if (Ext.isEmpty(fields)) {
            continue;
        }
        
        for (var k = 0; k < fields.length; k++) {
            field = fields[k];

            if (field.xtype == 'checkbox' || field instanceof Ext.form.Checkbox) { //弹窗
                values[fieldNames[i]] = field.getValue();
            } else if (field.xtype == 'compositefield') {
                ht.pub.getCheckboxValues(field.innerCt, fieldNames, values);
            }
        }
    }
    return values;
}
 
/**
 * 根据一个键和值（唯一）精确得到另一个键的值
 * @param {Ext.data.Store} store 
 * @param {String} key 
 * @param {String/Number} value
 * @param {String} otherKey
 * @return {String/Number}
 */
ht.pub.getValueExactByKey = function(store, currentKey, currentValue, otherKey){
    var index = store.findExactWithoutObj(currentKey, currentValue);
    if (index != -1) {
        var otherValue = store.getAt(index).get(otherKey);
        if (otherValue === ht.pub.store.BLANK_TEXT) {
            otherValue = '';
        }
        return otherValue;
    }else{
         return null;    
    }
}

/**
 * 默认VALUE -> TEXT获取值
 * @param {} store
 * @param {} currentValue
 * @return {}
 */
ht.pub.getValueExactByKeyDefault = function(store, currentValue){
    var currentKey = 'VALUE';
    var otherKey = 'TEXT';
    var index = store.findExactWithoutObj(currentKey, currentValue);
    if (index != -1) {
        var otherValue = store.getAt(index).get(otherKey);
        if (otherValue === ht.pub.store.BLANK_TEXT) {
            otherValue = '';
        }
        return otherValue;
    }else{
         return null;    
    }
}

/**
 * 根据一个键和多个值（以逗号分割）精确得到另一个键的多个值（以逗号分割）
 * @param {Ext.data.Store} store 
 * @param {String} key 
 * @param {String} currentValues
 * @param {String} otherKey
 * @return {String/Number}
 */
ht.pub.getValuesExactByKey = function(store, currentKey, currentValues, otherKey){
    var backValue = '';
    
    if (!Ext.isEmpty(currentValues, false)) {
        var vals = currentValues.split(',');
        
        for (var i = 0; i < vals.length; i++) {
            var value = vals[i];
            if (Ext.isEmpty(value, false)) {
                continue;
            }
            
            var text = ht.pub.getValueExactByKey(store, currentKey, value, otherKey);
            if (Ext.isEmpty(text)) {
                text = value;
            }
            
            backValue += text + ',';
            
        } 
            
        backValue += ',,,';
        backValue = backValue.replace(',,,,', '').replace(',,,', '');
    }
    
    return backValue;
}
/**
 * 默认VALUE -> TEXT获取值
 * @param {} store
 * @param {} currentValues
 * @return {}
 */
ht.pub.getValuesExactByKeyDefault = function(store, currentValues){
    var backValue = '';
    var currentKey = 'VALUE';
    var otherKey = 'TEXT';
    
    if (!Ext.isEmpty(currentValues, false)) {
        var vals = currentValues.split(',');
        
        for (var i = 0; i < vals.length; i++) {
            var value = vals[i];
            if (Ext.isEmpty(value, false)) {
                continue;
            }
            
            var text = ht.pub.getValueExactByKey(store, currentKey, value, otherKey);
            if (Ext.isEmpty(text)) {
                text = value;
            }
            
            backValue += text + ',';
            
        } 
            
        backValue += ',,,';
        backValue = backValue.replace(',,,,', '').replace(',,,', '');
    }
    
    return backValue;
}

//重写复选框的方法
Ext.override(Ext.form.Checkbox, {
    getValue : function(){
        if(this.rendered){
            return this.el.dom.checked ? 1 : 0;
        }
        return this.checked ? 1 : 0;
    }
});

//重写控件的可以使用的方法，加入权限控制
Ext.override(Ext.Component, {
    setDisabled : function(disabled){
        if (this.enableTag === false) { //权限
            disabled = true;
        }
        return this[disabled ? 'disable' : 'enable']();
    },
    enable : function(){
        
        if (this.enableTag === false) { //权限
            return this;
        }
        
        if(this.rendered){
            this.onEnable();
        }
        this.disabled = false;
        this.fireEvent('enable', this);
        return this;
    }
});

//重写复选框列过滤
Ext.override(Ext.ux.grid.filter.ListFilter, {
    validateRecord : function (record) {
        
        var val = record.get(this.dataIndex);
        if (this.rendererStore) {
             var valueField = this.valueField || 'VALUE';
             var displayField = this.displayField || 'TEXT';
             val = ht.pub.getValueExactByKey(this.rendererStore, valueField, val, displayField);
        }
        
        return this.getValue().indexOf(val) > -1;
    }
});

//重写字符串列过滤
Ext.override(Ext.ux.grid.filter.StringFilter, {
    validateRecord : function (record) {
        
        var val = record.get(this.dataIndex);
        if(typeof val != 'string') {
            val = '' + val;
//                return (this.getValue().length === 0);
        }
        
        if (this.rendererStore) {
             var valueField = this.valueField || 'VALUE';
             var displayField = this.displayField || 'TEXT';
             val = ht.pub.getValueExactByKey(this.rendererStore, valueField, val, displayField);
        }

        return val.toLowerCase().indexOf(this.getValue().toLowerCase()) > -1;
    }
});

//重写日期列过滤
Ext.override(Ext.ux.grid.filter.DateFilter, {
    validateRecord : function (record) {
        var key,
            pickerValue,
            val = record.get(this.dataIndex);
            
        if(!Ext.isEmpty(val)){
            return false;
        }
        
        if (Ext.isNumber(val)) {
        
        } else if (Ext.isString(val)) {
            val = Date.parseDate(val, this.dateFormat);
            val = val.clearTime(true).getTime();
        } else if (Ext.isDate(val)) {
            val = val.clearTime(true).getTime();
        } else {
            return false;
        }
        
        for (key in this.fields) {
            if (this.fields[key].checked) {
                pickerValue = this.getFieldValue(key).clearTime(true).getTime();
                if (key == 'before' && pickerValue <= val) {
                    return false;
                }
                if (key == 'after' && pickerValue >= val) {
                    return false;
                }
                if (key == 'on' && pickerValue != val) {
                    return false;
                }
            }
        }
        return true;
    }
});

//复合输入框
Ext.override(Ext.form.CompositeField, {
    invalidClass : '',
    onResize : function(w, h){
        Ext.form.CompositeField.superclass.onResize.call(this, w, h);
        
        var lastWidth = w;
        var i = 0;
        var compents = new Array();
        var comp = this.innerCt.getComponent(i);
        
        while (comp) {
            if (Ext.isEmpty(comp.width)) {
                compents[compents.length] = comp;
            } else {
                lastWidth -= comp.width;
            }
            
            i++;
            comp = this.innerCt.getComponent(i);
        }
        
        for (var k = 0; k < compents.length; k++) {
            compents[k].setWidth(lastWidth / compents.length);
        }
        this.innerCt.setWidth(w);
    }
});

Ext.namespace('ht.pub.date');
ht.pub.date.DATE = 'date';
ht.pub.date.DATE_TIME = 'datetime';
ht.pub.date.TIME = 'time';
ht.pub.date.REF_TYPE = 'REF_TYPE';

/**
 * 获取日期表格显示
 * @param {Number/String/Date} val 
 * @return {String}
 */
ht.pub.date.dateRenderer = function(val) {
    if (Ext.isEmpty(val, false)) {
        return '';
    }
    
    if (typeof val == 'object' ) {
        return val.format(ht.config.format.DATE);
    } else if (typeof val == 'number') {
        return new Date(val).format(ht.config.format.DATE);
    } else {
        
        return val;
    }
}

/**
 * 获取日期时间表格显示
 * @param {Number/String/Date} val
 * @return {String}
 */
ht.pub.date.dateTimeRenderer = function(val) {
    if (Ext.isEmpty(val, false)) {
        return '';
    }
    
    if (typeof val == 'object' ) {
        return val.format(ht.config.format.DATETIME);
    } else if (typeof val == 'number') {
        return new Date(val).format(ht.config.format.DATETIME);
    } else {
        return val;
    }
}

/**
 * 获取时间表格显示
 * @param {Number/String/Date} val
 * @return {String}
 */
ht.pub.date.timeRenderer = function(val) {
    if (Ext.isEmpty(val, false)) {
        return '';
    }
    
    if (typeof val == 'object') {
        return val.format(ht.config.format.TIME);
    } else if (typeof val == 'number') {
        return new Date(val).format(ht.config.format.TIME);
    } else {
        return val;
    }
}
/**
 * 获取时间表格显示
 * @param {Number/String/Date} val
 * @return {String}
 */
ht.pub.date.hourIRenderer = function(val) {
    if (Ext.isEmpty(val, false)) {
        return '';
    }
    
    if (typeof val == 'object') {
        return val.format(ht.config.format.HOURI);
    } else if (typeof val == 'number') {
        return new Date(val).format(ht.config.format.HOURI);
    } else {
        return val;
    }
}

/**
 * 格式化时间数据
 * private
 */
ht.pub.date.dateFormat = function(val, type) {
   if (type == ht.pub.date.DATE) {
       return ht.pub.date.dateRenderer(val);
   } else if (type == ht.pub.date.DATE_TIME) {
       return ht.pub.date.dateTimeRenderer(val);
   } else if (type == ht.pub.date.TIME) {
       return ht.pub.date.timeRenderer(val);
   } else {
        return val;
   }
}

/**
 * 格式化时间，但不马上显示到表格
 * @param {Array} storeFields
 * @param {Ext.data.JsonStore} store
 */
ht.pub.date.formatDateFields = function(storeFields, store) {
     var fields = new Array();
     
     //获取时间字段
     for (var i = 0; i < storeFields.length; i++) {
         if (typeof storeFields[i] == 'string') {
              continue;
         } else if (storeFields[i][ht.pub.date.REF_TYPE] == ht.pub.date.DATE 
                || storeFields[i][ht.pub.date.REF_TYPE] == ht.pub.date.DATE_TIME 
                || storeFields[i][ht.pub.date.REF_TYPE] == ht.pub.date.TIME) {
             fields[fields.length] = {name : storeFields[i].name, formate : storeFields[i][ht.pub.date.REF_TYPE]};
         }
     }
     
     if (0 == fields.length) {
         return;
     }
     
     //格式化时间，但不马上显示到表格
     for (var i = 0; i < store.getCount(); i++) {
         for (var k = 0; k < fields.length; k++) {
             var record = store.getAt(i);
             record.data[fields[k].name] =  ht.pub.date.dateFormat(record.data[fields[k].name], fields[k].formate);
         }
     }
}


/**
 * 根据权限（组件的tagValue属性）禁用控件
 * @param {Array} tagValues 用户所有权限
 * @param {Array} compents 组件
 */
ht.pub.disableCompentByTag = function(compents, tagValues) {
    if (!tagValues || !compents) {
        return;
    }
    
    for (var i = 0; i < compents.length; i++) {
        var comp = compents[i];
        if (comp.tagValue && tagValues.indexOf(comp.tagValue) == -1) {
            comp.disable(),
            comp.enableTag = false;
        } else {
            comp.enable(),
            comp.enableTag = true;
        }
    }
}

/**
 * 根据表格行是否选中启用、禁用组件（组件属性：enableByRowSelected）
 * @param {Array} compents 组件
 * @param {Boolean} rowSelected 表格行是否选中
 */
ht.pub.enableCompentByRowSelected = function(compents, rowSelected) {
    
    if (!compents) {
        return;
    }
    
    for (var i = 0; i < compents.length; i++) {
        var comp = compents[i];
        if (comp.enableByRowSelected === true && comp.enableTag !== false) {
            if (rowSelected) {
               comp.enable();
            } else {
               comp.disable();
            }
        }
    }
}

/**
 * 启用、禁用组件
 * @param {Array} compents 组件
 * @param {Boolean} enable 是否启用
 * @param {String} prop 控件属性
 * @param {String} propValue 控件的值
 */
ht.pub.enableCompents = function(compents, enable, prop, propValue) {
    
    if (!compents) {
        return;
    }
    
    for (var i = 0; i < compents.length; i++) {
        var comp = compents[i];
        if (comp.enableTag !== false) {
            if (enable) {
                if (prop) {
                    if (comp[prop] === propValue) {
                        comp.enable();
                    }
                } else {
                    comp.enable();
                }
            } else {
                if (prop) {
                    if (comp[prop] === propValue) {
                        comp.disable();
                    }
                } else {
                    comp.disable();
                }
               
            }
        }
    }
}

/**
 * 根据是否有数据启用、禁用组件（组件属性：enableByHasData）
 * @param {Array} compents 组件
 * @param {Boolean} hasData 是否有数据
 */
ht.pub.enableCompentByHasData = function(compents, hasData) {
    
    if (!compents) {
        return;
    }
    
    for (var i = 0; i < compents.length; i++) {
        var comp = compents[i];
        if (comp.enableByHasData && comp.enableTag !== false) {
            if (hasData) {
               comp.enable();
            } else {
               comp.disable();
            }
        }
    }
}

/**
 * 根据组件的属性启用、禁用输入组件（组件属性：insertable，updateable）
 * @param {Object} parentCompent 输入组件的父组件
 * @param {Array} fieldNames 输入组件的name属性
 * @param {Boolean} isUpdate 是否是修改，true：修改，false：新增
 */
ht.pub.disableFields = function(parentCompent, fieldNames, isUpdate) {
    
    //参数定义
    var fields;
    var field;
    var fieldCls;
    var CLS_READONLY = ht.config.style.CLS_READONLY;
    var CLS_REQUIRE = ht.config.style.CLS_REQUIRE;
    
    for (var i = 0; i < fieldNames.length; i++) {
        if (Ext.isEmpty(fieldNames[i], false)) {
            continue;
        }

        fields = parentCompent.find('name', fieldNames[i]);
        if (!fields || fields.length == 0) {
            continue;
        }
        
        
        for (var k = 0; k < fields.length; k++) {
            field = fields[k];
            
            if (field.xtype == 'compositefield') {
                ht.pub.disableFields(field.innerCt, fieldNames, isUpdate);
                continue;
            }
            
            field.removeClass(CLS_READONLY);
            field.removeClass(CLS_REQUIRE);
            fieldCls = '';

            if ((field.insertable === false && !isUpdate) 
                 || (field.updateable === false && isUpdate)) {
                    
                fieldCls = CLS_READONLY; //只读   
               
                if (field.xtype == 'combo' || field instanceof Ext.form.ComboBox) {  //下拉框
                    if (field.editable !== false) {
                        field.defaultEditable = true;
                    } else {
                        field.defaultEditable = false;
                    }
                 
                } else if (field.xtype == 'checkbox' || field instanceof Ext.form.Checkbox
                       || field.xtype == 'radio' || field instanceof Ext.form.Radio
                       || field.xtype == 'checkboxgroup' || field instanceof Ext.form.CheckboxGroup
                       || field.xtype == 'radiogroup' || field instanceof Ext.form.RadioGroup) { //多选、单选
    
                     field.disable();
                     fieldCls = '';
                     
                } else if (field.xtype == 'textfieldbutton' || field instanceof ht.base.TextFieldButton) { //弹窗
                    
                    if (field.hideButtonOnReadOnly !== false) {
                        field.hideButton(true);
                    }
                }
                
                field.setReadOnly(true);
            } else {
                
                //必须项
                if (field.allowBlank === false) {
                    fieldCls = CLS_REQUIRE;
                }
                
                if (field.xtype == 'combo' || field instanceof Ext.form.ComboBox) { //下拉框
                    if (field.defaultEditable !== undefined && field.defaultEditable !== null){
                        field.setEditable(field.defaultEditable);
                    }
                    field.setReadOnly(false);
                    
                } else if (field.xtype == 'checkbox' || field instanceof Ext.form.Checkbox
                       || field.xtype == 'radio' || field instanceof Ext.form.Radio
                       || field.xtype == 'checkboxgroup' || field instanceof Ext.form.CheckboxGroup
                       || field.xtype == 'radiogroup' || field instanceof Ext.form.RadioGroup) { //单选、多选
    
                     field.enable();
                     fieldCls = '';
                     field.setReadOnly(false);
                     
                } else if (field.xtype == 'textfieldbutton' || field instanceof ht.base.TextFieldButton) { //弹框
                    
                     //是否可编辑
                     if (field.defaultEditable !== undefined && field.defaultEditable !== null){
                        field.setEditable(field.defaultEditable);
                     }
                     
                     if (field.editable === false) {
                         field.setReadOnly(true);
                     } else {
                         field.setReadOnly(false);
                     }
                     
                     field.hideButton(false);
                     
                } else {
                     field.setReadOnly(false);
                }
            }
            if ('' != fieldCls) {
                field.addClass(fieldCls);
            }
        }
    }
}

/**
 * 失去焦点时，去除输入框值的左右空格
 * @param {Object} parentCompent 输入组件的父组件
 * @param {Array} fieldNames 输入组件的name属性
 */
ht.pub.trimFields = function(parentCompent, fieldNames) {
    
    //参数定义
    var fields;
    var field;
    var isRowEditor = parentCompent instanceof Ext.ux.grid.RowEditor; 
    
    for (var i = 0; i < fieldNames.length; i++) {
        fields = parentCompent.find('name', fieldNames[i]);
        if (!fields || fields.length == 0) {
            continue;
        }
        
        for (var k = 0; k < fields.length; k++) {
            field = fields[k];
            
            if (field.xtype == 'compositefield') {
                ht.pub.trimFields(field.innerCt, fieldNames);
                continue;
            }
            
            if (field.isInitAutoTrimListener) {
                continue;
            }
            field.isInitAutoTrimListener = true;
            
            //失去焦点事件
            field.on('blur', function(blurField) {
                if (blurField.autoTrim !== false 
                   && !(blurField.xtype == 'combo' || blurField instanceof Ext.form.ComboBox)) {
                    
                    var value = blurField.getValue();
                    if (Ext.isEmpty(value)) {
                        value = '';
                    }
                    if ( (typeof value == 'string') && value.trim() != blurField.getValue()) {
                        blurField.setValue(value.trim());
                    }
                }
            });

        }
    }
}

/**
 * 增加备注信息
 * @param {} parentCompent
 * @param {} fieldNames
 */
ht.pub.remarkFields = function(parentCompent, fieldNames) {
    
    //参数定义
    var fields;
    var field;
    var fieldDom;
    var fieldParentDom;
    
    for (var i = 0; i < fieldNames.length; i++) {
        if (Ext.isEmpty(fieldNames[i], false)) {
            continue;
        }
        
        fields = parentCompent.find('name', fieldNames[i]);
        if (!fields || fields.length == 0) {
            continue;
        }
        
        for (var k = 0; k < fields.length; k++) {
            field = fields[k];
            if (field.xtype == 'compositefield') {
                ht.pub.remarkFields(field.innerCt, fieldNames);
                continue;
            }
            
            
            fieldDom = Ext.getDom(field.id);
            if (!fieldDom) {
                continue;
            }

            if ('INPUT' != fieldDom.tagName || !field.remarkWidth) {
               continue;
            }
            
            if (!fieldDom.getAttribute('DEFALUT_STYLE_WIDTH')) {
                fieldDom.setAttribute('DEFALUT_STYLE_WIDTH', parseInt('' + fieldDom.style.width));
                field.alignErrorIcon = function(){
                    var errorWidth = this.remarkWidth;
                    if (this.xtype == 'combo' || this instanceof Ext.form.ComboBox
                        || this.xtype == 'timefield' || this instanceof Ext.form.TimeField
                        || this.xtype == 'datefield' || this instanceof Ext.form.DateField) {
                        errorWidth += 17;
                    }
                    this.errorIcon.alignTo(this.el, 'tl-tr', [errorWidth, 0]);
                }
            }
            
            var width = (parseInt('' + fieldDom.getAttribute('DEFALUT_STYLE_WIDTH')) || 0) - field.remarkWidth;
            if (width > 0) {
                fieldDom.style.width = width + 'px';
            }
            
            if (document.getElementById(field.id + '_remark_ct')) {
                continue;
            } 
            
            fieldParentDom = fieldDom.parentElement || fieldDom.parentNode;
            
            var div = document.createElement('div');
            div.id = field.id + '_remark_ct';
            div.className = 'ht-remark ' + (field.remarkCls || '');
            div.innerHTML = field.remarkText || '';
            fieldParentDom.appendChild(div)
            
        }
    }
}

/**
 * 转换复选框的值为 0 1
 * @param {Boolean/String} originalValue
 * @return {Number}
 */
ht.pub.getCheckboxValue = function(originalValue) {
    originalValue = '' + originalValue;
    if ('true' == originalValue
        || '1' == originalValue || 'on' == originalValue) {
        return 1;
    }
    
    return 0;
}

/**
 * 根据名称-值属性生成标签
 * @param {} tabPanel TAB面板
 * @param {} title 标题
 * @param {} formProperies 属性
 * @param {} isActive 是否可活动，默认活动
 * @param {} enable 是否可编辑，默认不可编辑
 */
ht.pub.addTab = function(tabPanel, title, formProperies, isActive, enable) {
    
    //创建属性面板子项
    var items = [];
    for (var field in formProperies) {
        items.push({
            xtype : 'textfield',
            fieldLabel : field,
            name : 'FP_' + items.length,
            value : formProperies[field],
            insertable : enable === true
        })
    }
    
    //创建属性面板
    var propertyPanel = new ht.base.PropertyPanel({
        border : false,
        formConfig : {
            query : {
                enableQueryButton : false
            },
            useTo : 'property', 
            items : items
        }
    });
    
    //获取标签,并增加属性面板
    var tabItemPanel = tabPanel.find('title', title);
    tabItemPanel = tabItemPanel.length > 0 ? tabItemPanel[0] : null;
    if (tabItemPanel) {
        tabItemPanel.removeAll(true);
        tabItemPanel.add(propertyPanel);
        tabItemPanel.doLayout();
    } else {
        tabPanel.add({
            title : title,
            layout : 'fit',
            items : propertyPanel, 
            listeners : {
                activate : function(panel){
                    panel.doLayout();
                }
            }
        });
    }
    
    if (isActive !== false) {
        tabPanel.setActiveTab(propertyPanel.ownerCt);
    }
    
}

 /**
 * 将空格转换成html的&nbsp;
 * @param {String} value
 */
ht.pub.getBlankSpaceToNbsp = function(value) {
    if(typeof(value) == 'string'){
        if (!Ext.isEmpty(value)) {
            return value.replace(/[ ]/g, '&nbsp;');
        }
    } 
    
    return value;
}

//重写
Ext.override(Ext.grid.Column, {
    renderer: function(value, metaData, record, rowIndex, colIndex, store) {
        return ht.pub.getBlankSpaceToNbsp(value);
    }
});


