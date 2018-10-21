import React, { Component } from 'react';
import {
  Platform,
  StyleSheet,
  Text,
  View,
  ListView,
  ScrollView,
  Button,
  Dimensions,
  TextInput,
  TouchableOpacity,
  TouchableHighlight,
  ToolbarAndroid,
  Alert,
  Image,
} from 'react-native';
import {Navigator} from 'react-native-deprecated-custom-components';
import TabNavigator from 'react-native-tab-navigator';
// import {Dimensions} from 'react-native';
/**
 * 导入另一个js文件 ,用来实现页面的跳转
 */
import GoodsShow from './GoodsShow';
import CartShow from './CartShow';
import GoodClassify from './GoodClassify';
import my from './my';
/**
 * 从json文件中获取数据
 */
var data = require('./All.json');
/**
 * 创建一个数组
 */
var dataPart = new Array(); 
/**
 * 写一个数组用来克服,本地图片不能使用变量来添加的问题,,笨方法
 */

const img_arr = [require('./res/image/good1_icon.jpg'),require('./res/image/good2_icon.jpg'),require('./res/image/good3_icon.jpg'),
require('./res/image/good4_icon.jpg'),require('./res/image/good5_icon.jpg'),require('./res/image/good6_icon.jpg'),
require('./res/image/good7_icon.jpg'),require('./res/image/good8_icon.jpg'),require('./res/image/good9_icon.jpg'),require('./res/image/good10_icon.jpg'),
require('./res/clothes/clothes1.jpg'),require('./res/clothes/clothes2.jpg'),require('./res/clothes/clothes3.jpg'),
require('./res/clothes/clothes4.jpg'),require('./res/clothes/clothes5.jpg'),require('./res/clothes/clothes6.jpg'),
require('./res/clothes/clothes7.jpg'),require('./res/clothes/clothes8.jpg'),require('./res/clothes/clothes9.jpg'),
require('./res/computerImage/computer1.jpg'),require('./res/computerImage/computer2.jpg'),require('./res/computerImage/computer3.jpg'),
require('./res/computerImage/computer4.jpg'),require('./res/computerImage/computer5.jpg'),require('./res/computerImage/computer6.jpg'),
require('./res/computerImage/computer7.jpg'),require('./res/computerImage/computer8.jpg'),require('./res/computerImage/computer9.jpg'),
require('./res/shoeImage/shoe1.jpg'),require('./res/shoeImage/shoe2.jpg'),require('./res/shoeImage/shoe3.jpg'),
require('./res/shoeImage/shoe4.jpg'),require('./res/shoeImage/shoe5.jpg'),require('./res/shoeImage/shoe6.jpg'),
require('./res/shoeImage/shoe7.jpg'),require('./res/shoeImage/shoe8.jpg'),require('./res/shoeImage/shoe9.jpg')]
/**
 * 总钱数
 */
var money = 0;

var cartNew = new Array();
/**
 * 用来表示这个订单是第一次进来
 */
var flag = 0;
/**
 * 获取设备屏幕的宽和高
 */
var {height,width} = Dimensions.get('window');
export default class OrderShow extends Component {
    constructor(props) {
        super(props);
        
       for(var i = 0; i < this.props.cart.length;i++) {
           /**
            * 添加对象数组要用push
            */
           cartNew.push(this.props.cart[i] - 1 );
           dataPart.push( data[this.props.cart[i] - 1] );
       }
       
      
           if( flag !==  this.props.cart.length) {
                for( var i = 0; i < this.props.cart.length; i++ ){
                     money = money+dataPart[i].price; 
                }
                flag = this.props.cart.length;
           }
            
      
        var ds = new ListView.DataSource({rowHasChanged: (r1,r2) => r1 !== r2});
        this.state = {
            dataSource: ds.cloneWithRows(dataPart),
            selectedTab: 'OrderShow',
        }
    }
    render() {
        if(this.props.cart.length != 0) {
         return( 
             <View style={styles.container}>
                 <View>
                  <ToolbarAndroid 
                   style={styles.toolbar}
                   navIcon={require('./res/image/back_icon.png')} 
                   title='订单'
                //    subtitle='商品列表'
                actions={[{title: '商品列表' },
                {title: '订单列表'} ,
                {title: '购物车'}]}
            // 使用这个将坐标传过去了.在函数中用参数接受就可以了
               onActionSelected={this.toolBaronActionSelected.bind(this)}
               onIconClicked={this.backToBefore.bind(this)}
                /> 
                </View>
                <View>

                
                 <ListView 
                 dataSource={this.state.dataSource}
                 renderRow={this.renderRow.bind(this)}
                 >

                 </ListView>
                 </View>
                 
                 {/* <Text>
                     {dataPart[0].id}
                 </Text> */}
                {/* <Text>
                    {this.props.cart.length}
                </Text> */}
                 <View style={styles.toOrderView}>
                    <TouchableOpacity 
                    activeOpacity={0.5}
                    style={styles.toOrderButton}
                      onPress={() => this.success(money) }
                    >
                        <Text style={{marginTop:5, fontSize:18}}>
                            支付:${money}
                        </Text>
                    </TouchableOpacity>
                </View>

             <TabNavigator>
                {this.tabNavigator('GoodClassify', require('./res/icon/index20.jpg') , '首页',0)}
                {this.tabNavigator('CartShow', require('./res/icon/cart20px.jpg'), '购物车',2)}
                {this.tabNavigator('OrderShow', require('./res/icon/order20.jpg'),'订单',1)}
                {this.tabNavigator('my', require('./res/icon/wode20.jpg') , '我的',3)}
            </TabNavigator>
             </View>
            
         );
        }else {
          return (
              <View style={styles.container}>
                    <ToolbarAndroid 
                   style={[styles.toolbar]}
                   navIcon={require('./res/image/back_icon.png')} 
                   title='订单'
                //    subtitle='商品列表'
                actions={[{title: '商品列表' },
                {title: '订单列表'} ,
                {title: '购物车'}]}
            // 使用这个将坐标传过去了.在函数中用参数接受就可以了
               onActionSelected={this.toolBaronActionSelected.bind(this)}
               onIconClicked={this.backToBefore.bind(this)}
                /> 
              
            <View style={{justifyContent:'center', alignItems: 'center'}}>
            
            <Text style={ {fontSize:26,fontStyle:'italic'}}>
                好像走错地方了*********
            </Text>
            </View>
            <TabNavigator>
                {this.tabNavigator('GoodClassify', require('./res/icon/index20.jpg') , '首页',0)}
                {this.tabNavigator('CartShow', require('./res/icon/cart20px.jpg'), '购物车',2)}
                {this.tabNavigator('OrderShow', require('./res/icon/order20.jpg') ,'订单',1)}
                {this.tabNavigator('my', require('./res/icon/wode20.jpg') , '我的',3)}

            </TabNavigator>
            
            </View>
          );
        }
    }

    tabNavigator(selectTable , iconUrl , title , index ) {
        return (

                <TabNavigator.Item
                        selected={this.state.selectedTab === selectTable }
                        title={title}
                        //选中时的title的样式
                        selectedTitleStyle={{color:"#007aff"}}//设置tab标题颜色
                        renderIcon={() => <Image style={styles.icon} source={iconUrl} />}
                        renderSelectedIcon={() => <Image style={[styles.icon,{tintColor:'#007aff'}]} source={ iconUrl } />}//设置图标选中颜色
                        // badgeText="1"
                        onPress={() => this.toolBaronActionSelected(index)}>
                        <Text></Text>
                    </TabNavigator.Item>
        );
    }
    _navigateClassify(cart,type = 'Normal') {
        /**
         * 前面的是名称,后面的是要显示的值,一定要用Alert.alert的格式,并且 要导入Alert
         */
            this.props.navigator.push({
              component: GoodClassify,
              passProps: {
                cart:cart
              },
              type: type
            })
          }
    success(money) {
        var num = this.props.cart.length;
        for(var i = 0; i < num ; i++ ){
            dataPart.pop();
            this.props.cart.pop();
        }
       
        Alert.alert("支付成功",money + '');
        money = 0;
        var cart = [];
        this._navigateClassify(cart);
    }
     // 返回一个Item
     renderRow(rowData){
         
         return(
            
             <View style={styles.itemStyle}>       
                 <Image source={img_arr[cartNew.shift()]} style={styles.imageStyle}/>
                 <View style={{flexDirection:'column'}}>
                     <Text style={{marginTop:5, fontSize:17}}>{rowData.title}</Text>
                     <Text style={{marginBottom:5, fontSize:13, color:'orange'}}>{rowData.intro}</Text>
                     <Text style={{marginBottom:5, fontSize:13, color:'red'}}>{rowData.price}</Text>
                 </View>
             </View>
         );
     }
     //position 坐标 0,1,2 三个
    /**
     * 导航栏右侧的跳转
     */
    toolBaronActionSelected(position) {
        for(var i = 0; i < this.props.cart.length ; i++ ){
            dataPart.pop();
        }
        if(position == 0) {
           this._navigate0();
        }
        if( position == 2) {
            this._navigate2(this.props.cart);
        }
        if(position == 1) {
             this._navigate1(this.props.cart);
        }
        if( position === 3) {
            this._navigate3(this.props.cart);
        }
   }
  
   /**
    * 如果是写在一个函数里,调用这个函数的时候要使用一个回调bind
    * 返回上一页
    */
   backToBefore() {
    for(var i = 0; i < this.props.cart.length ; i++ ){
        dataPart.pop();
    }
       this.props.navigator.pop();
   }
              /**
  * 给Navigator传递参数.
  * @param name 参数
  * @private 
  * type 有值表示如果没有值传过来的话就使用这个默认的值,有的话就替换为传过来的值
  */
 _navigate2(cart,type = 'Normal') {
   /**
    * 前面的是名称,后面的是要显示的值,一定要用Alert.alert的格式,并且 要导入Alert
    */
       this.props.navigator.push({
         component: CartShow,
         passProps: {
           cart:cart
         },
         type: type
       })
     }
     _navigate3(cart,type = 'Normal') {
        /**
         * 前面的是名称,后面的是要显示的值,一定要用Alert.alert的格式,并且 要导入Alert
         */
        
            this.props.navigator.push({
              component: my,
              passProps: {
                cart:cart
              },
              type: type
            })
          }
     _navigate0(cart,type = 'Normal') {
       /**
        * 前面的是名称,后面的是要显示的值,一定要用Alert.alert的格式,并且 要导入Alert
        */
           this.props.navigator.push({
             component: GoodsShow,
             passProps: {
               cart:cart
             },
             type: type
           })
         }
   /**
    * 跳转订单页
    * @param {*} cart 
    * @param {*} type 
    */
         _navigate1(cart,type = 'Normal') {
           /**
            * 前面的是名称,后面的是要显示的值,一定要用Alert.alert的格式,并且 要导入Alert
            */
               this.props.navigator.push({
                 component: OrderShow,
                 passProps: {
                   cart:cart
                 },
                 type: type
               })
             }
}
const styles = StyleSheet.create ({
    container: {
        flex: 1,
    },
    toolbar: {
        width: width,
        height: 60,
        backgroundColor: 'skyblue',
        marginTop: 0,
    },
    itemStyle: {
        // 主轴方向
        flexDirection:'row',
        // 下边框
        borderBottomWidth:1,
        borderBottomColor:'gray',
    },
    imageStyle: {
        // 尺寸
        width:72,
        height:72,
        // 边距
        marginLeft:10,
        margin:10
    },
    toOrderView: {
       
        flexDirection: 'row-reverse',
    },
    toOrderButton: {
        
        backgroundColor: 'red',
        
    },
});