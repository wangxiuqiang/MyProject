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
// import {Dimensions} from 'react-native';
/**
 * 导入另一个js文件 ,用来实现页面的跳转
 */
import GoodsShow from './GoodsShow';
// import CartShow from './CartShow';
import OrderShow from './OrderShow';
/**
 * 获取设备屏幕的宽和高
 */
var {height,width} = Dimensions.get('window');
/**
 * 从json文件中获取数据
 */
var data = require('./Goods.json');
/**
 * 创建一个数组
 */
var dataPart = new Array(); 
var cartNew = new Array();
/**
 * 写一个数组用来克服,本地图片不能使用变量来添加的问题,,笨方法
*/
const img_arr = [require('./image/good1_icon.jpg'),require('./image/good2_icon.jpg'),require('./image/good3_icon.jpg'),
require('./image/good4_icon.jpg'),require('./image/good5_icon.jpg'),require('./image/good6_icon.jpg'),
require('./image/good7_icon.jpg'),require('./image/good8_icon.jpg'),require('./image/good9_icon.jpg'),require('./image/good10_icon.jpg')]
export default class CartShow extends Component {
    constructor(props) {
        super(props);
        
       for(var i = 0; i < this.props.cart.length;i++) {
           /**
            * 添加对象数组要用push
            */
           cartNew.push(this.props.cart[i] - 1 );
           dataPart.push( data[this.props.cart[i] - 1 ] );
       }
        
        var ds = new ListView.DataSource({rowHasChanged: (r1,r2) => r1 !== r2});
        this.state = {
            dataSource: ds.cloneWithRows(dataPart),
        }
    }
    render() {
        // return ( 
        //     <View>
        //         <View>
        //         <ToolbarAndroid 
        //            style={styles.toolbar}
        //            navIcon={require('./image/back_icon.png')} 
        //            title='返回'
        //         //    subtitle='商品列表'
                
        //        onIconClicked={this.backToBefore.bind(this)}
        //         /> 
        //         </View>
        //         <View>
        //              <Text>
        //             {this.props.cart}
        //         </Text>
        //         </View>
               
        //     </View>
        // ) 
        if(this.props.cart.length != 0) {
         return( 
             <View>
                  <ToolbarAndroid 
                   style={styles.toolbar}
                   navIcon={require('./image/back_icon.png')} 
                   title='返回'
                //    subtitle='商品列表'
                actions={[{title: '商品列表' },
                {title: '订单列表'} ,
                {title: '购物车'}]}
            // 使用这个将坐标传过去了.在函数中用参数接受就可以了
               onActionSelected={this.toolBaronActionSelected.bind(this)}
               onIconClicked={this.backToBefore.bind(this)}
                /> 
                
                 <ListView 
                 dataSource={this.state.dataSource}
                 renderRow={this.renderRow.bind(this)}
                 >

                 </ListView>
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
                      onPress={() => this._navigateOrder(this.props.cart) }
                    >
                       <Text style={{marginTop:5, fontSize:17}}>
                           生成订单
                       </Text>
                    </TouchableOpacity>
                </View>
             </View>
            
         );
        }else {
            return (
                <View>
                    <ToolbarAndroid 
                   style={[styles.toolbar]}
                   navIcon={require('./image/back_icon.png')} 
                   title='返回'
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
            </View>
            );
        }
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
   }
   /**
    * 跳转到Order的界面
    */
   _navigateOrder(cart,type = 'Normal') {
    /**
     * 前面的是名称,后面的是要显示的值,一定要用Alert.alert的格式,并且 要导入Alert
     */
    for(var i = 0; i < this.props.cart.length ; i++ ){
        dataPart.pop();
    }
        this.props.navigator.push({
          component: OrderShow,
          passProps: {
            cart:cart
          },
          type: type
        })
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
                 component: CartShow,
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