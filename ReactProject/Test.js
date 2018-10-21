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
import TabNavigator from 'react-native-tab-navigator'

export default class Test extends Component {
    constructor(props) {
        super(props);
        
      
        this.state = {
          selectedTab: 'GoodsShow'
        }
    }
    render() {
        return ( 
            <View style={styles.container}>
                    
            <TabNavigator >
                <TabNavigator.Item
                        selected={this.state.selectedTab === 'GoodsShow'}
                        title="首页"
                        //选中时的title的样式
                        selectedTitleStyle={{color:"#007aff"}}//设置tab标题颜色
                        renderIcon={() => <Image style={styles.icon} source={require('./res/icon/index20.jpg')} />}
                        renderSelectedIcon={() => <Image style={[styles.icon,{tintColor:'#007aff'}]} source={require('./res/icon/index20.jpg')} />}//设置图标选中颜色
                        // badgeText="1"
                        onPress={() => this.setState({ selectedTab: 'GoodsShow' })}>
                        <Text>123</Text>
                    </TabNavigator.Item>
                    <TabNavigator.Item
                        selected={this.state.selectedTab === 'CartShow'}
                        title="购物车"
                        //选中时的title的样式
                        selectedTitleStyle={{color:"#007aff"}}//设置tab标题颜色
                        renderIcon={() => <Image style={styles.icon} source={require('./res/icon/cart20px.jpg')} />}
                        renderSelectedIcon={() => <Image style={[styles.icon,{tintColor:'#007aff'}]} source={require('./res/icon/index20.jpg')} />}//设置图标选中颜色
                        // badgeText="1"
                        onPress={() => this.setState({ selectedTab: 'CartShow' })}>
                       <Text>123</Text>
                    </TabNavigator.Item>
                </TabNavigator>
            </View>
        );
    }
}
const styles = StyleSheet.create ({
    container: {
        flex: 1,
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
    addButtonView: {
        flex:1,
        flexDirection: 'row-reverse',
    }
    ,
    buttonAdd: {
        backgroundColor: 'skyblue',
       
        width:80,
        height:20,
    },
    icon:{
        width:20,
        height:20
      }

}); 