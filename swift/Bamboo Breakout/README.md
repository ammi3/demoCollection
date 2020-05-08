### Bamboo

### 1：游戏状态

​	我们先设这个游戏有三种状态

1. 游戏初始化：`WaitingForTip`
2. 游戏进行时：`Playing`
3. 游戏结束：`GameOver`

### 2：添加物体

#### 2.1：添加小球

​	在`GameScene.sks`中拖拽一个`Color Sprite`到场景中，命名为**ball**

#### 2.2：添加木板

​	在`GameScene.sks`中拖拽一个`Color Sprite`到场景中，命名为**paddle**，并设置属性

#### 2.3：移动木板

- 移动木板需要检测触摸，所以我们可以通过`touch`回调方法来检测触摸

> 首先，我们需要添加一个属性`var isFingerOnPaddle = false`，这是为了用来保存玩家是否触摸到了木板

1. 开始触摸的时候

   ```swift
   // 这时候我们需要在移动木板的时候先去判断游戏的状态，不同游戏状态的点击会有不同的效果
   override func touchesBegan(_ touches: Set<UITouch>, with event: UIEvent?) {
       switch gameState.currentState {
       // 当处于游戏初始化状态时，点击后进入游戏进行时
       case is WaitingForTap:
         gameState.enter(Playing.self)
         isFingerOnPaddle = true
       //当处于游戏进行中时，我们此时要移动木板，又因为是绝对反弹，所以应该去记住开始点击时的位置以便之后可以计算反弹的角度
       case is Playing:
         let touch = touches.first
         let touchLocation = touch!.location(in: self)
           //判断该位置是否存在物理体
         if let body = physicsWorld.body(at: touchLocation) {
             //如果存在物理体，判断该物理体是否时木板
           if body.node!.name == PaddleCategoryName {
             isFingerOnPaddle = true
           }
         }
       // 当处于游戏结束时，
       case is GameOver:
         let newScene = GameScene(fileNamed:"GameScene")
         newScene!.scaleMode = .aspectFit
         let reveal = SKTransition.flipHorizontal(withDuration: 0.5)
         self.view?.presentScene(newScene!, transition: reveal)
       default:
         break
       }
     }
   ```

2. 触摸移动的过程中

   ```swift
   override func touchesMoved(_ touches: Set<UITouch>, with event: UIEvent?) {
       // 判断是否触摸到了木板
       if isFingerOnPaddle {
         // 更新木板的位置
         let touch = touches.first
         let touchLocation = touch!.location(in: self)
         let previousLocation = touch!.previousLocation(in: self)
         // 获取木板对应的SpriteNode
         let paddle = childNode(withName: PaddleCategoryName) as! SKSpriteNode
         // 用木板当前位置加上两次触摸位置之差
         var paddleX = paddle.position.x + (touchLocation.x - previousLocation.x)
         // 限制木板的位置，防止它移出屏幕左右两边。
         paddleX = max(paddleX, paddle.size.width/2)
         paddleX = min(paddleX, size.width - paddle.size.width/2)
         // 根据之前计算的结果，设置木板的位置。
         paddle.position = CGPoint(x: paddleX, y: paddle.position.y)
       }
     }
   ```

3. 触摸结束

   ```swift
   override func touchesEnded(_ touches: Set<UITouch>, with event: UIEvent?) {
       // 设置玩家没有触摸到木板这个状态
       isFingerOnPaddle = false
     }
   ```

#### 2.4：添加竹子

​	Bamboo Block

​	我们在`didMove(to:)`中加入下面的内容

```swift
// 1.设置添加竹子的块数，以及宽度
let numberOfBlocks = 8
let blockWidth = SKSpriteNode(imageNamed: "block").size.width
let totalBlocksWidth = blockWidth * CGFloat(numberOfBlocks)
// 2.计算偏移量
let xOffset = (frame.width - totalBlocksWidth) / 2
// 3.创建竹子，添加物理引擎，并通过xOffset来定位竹子的位置
for i in 0..<numberOfBlocks {
  let block = SKSpriteNode(imageNamed: "block.png")
  block.position = CGPoint(x: xOffset + CGFloat(CGFloat(i) + 0.5) * blockWidth, 
    y: frame.height * 0.8)
      
  block.physicsBody = SKPhysicsBody(rectangleOf: block.frame.size)
  block.physicsBody!.allowsRotation = false
  block.physicsBody!.friction = 0.0
  block.physicsBody!.affectedByGravity = false
  block.physicsBody!.isDynamic = false
  block.name = BlockCategoryName
  block.physicsBody!.categoryBitMask = BlockCategory
  block.zPosition = 2
  addChild(block)
}
```



### 3：物理世界

#### 3.1：为小球添加物理引擎

1. 选中`ball`在属性面板中，找到`Physics Definition`处，分别为属性设置以下值

2. 此时，小球可以向下掉落了，但是会发现小球会一直掉出这个世界，故：我们要为这个物理世界加入一个边界以至于小球不可以掉出边界

   ```swift
   // 在GameScene.swift的didMove(to:)中添加以下内容
   // 创建一个边界物体
   let borderBody = SKPhysicsBody(edgeLoopFrom: self.frame)
   // Friction 设置为 0，这样球体与之碰撞后不会速度衰减
   borderBody.friction = 0
   self.physicsBody = borderBody
   ```

3. 这个时候，小球已经可以无限的反弹了；但是我们需要的是绝对反弹，也就是击中时以相同的角度离开

   ```swift
   // 在GameScene.swift的didMove(to:)方法中，添加以下内容
   // 去除场景中的重力加速度
   physicsWorld.gravity = CGVector(dx: 0.0, dy: 0.0)
   let ball = childNode(withName: BallCategoryName) as! SKSpriteNode
   ball.physicsBody!.applyImpulse(CGVector(dx: 2.0, dy: -2.0))
   ```

#### 3.2：制造碰撞

1. 此时，我们已经有了一个可以弹跳的小球和一块可以移动的木板。设定规则，小球碰到地面则是失败

2. 设置物理体的掩码

   ```swift
   let BallCategory   : UInt32 = 0x1 << 0
   let BottomCategory : UInt32 = 0x1 << 1
   let BlockCategory  : UInt32 = 0x1 << 2
   let PaddleCategory : UInt32 = 0x1 << 3
   let BorderCategory : UInt32 = 0x1 << 4
   ```

3. 创建`categoryBitMasks`，`contactTestBitMask`

   ```swift
   let paddle = childNode(withName: PaddleCategoryName) as! SKSpriteNode
   bottom.physicsBody!.categoryBitMask = BottomCategory
   ball.physicsBody!.categoryBitMask = BallCategory
   paddle.physicsBody!.categoryBitMask = PaddleCategory
   borderBody.categoryBitMask = BorderCategory
   // 要考虑小球与木板，底部，竹子，四周的碰撞
   ball.physicsBody!.contactTestBitMask = BottomCategory | BlockCategory | BorderCategory | PaddleCategory
   ```

4. 设置`GameScene`为所有物理碰撞的委托

   ```swift
   physicsWorld.contactDelegate = self
   ```

5. 处理碰撞事件：`didBegin(_:)`方法

   ```swift
   func didBegin(_ contact: SKPhysicsContact) {
       if gameState.currentState is Playing {
         // 1.用于保存发生碰撞的两个物体
         var firstBody: SKPhysicsBody
         var secondBody: SKPhysicsBody
         // 2.检查两个物体的categoryBitMask，小的存放在firstbody中
         if contact.bodyA.categoryBitMask < contact.bodyB.categoryBitMask {
           firstBody = contact.bodyA
           secondBody = contact.bodyB
         } else {
           firstBody = contact.bodyB
           secondBody = contact.bodyA
         }
         // 判断发生碰撞的两个物体是否为BallCategory和BottomCategory
         if firstBody.categoryBitMask == BallCategory && secondBody.categoryBitMask == BottomCategory {
           // 是的情况下：结束游戏
           gameState.enter(GameOver.self)
           gameWon = false
         }
        // 和竹子Block发生碰撞的条件下：竹子Block消失，并且判断Block是否存在，若不存在，结束游戏
         if firstBody.categoryBitMask == BallCategory && secondBody.categoryBitMask == BlockCategory {
           breakBlock(secondBody.node!)
           if isGameWon() {
             gameState.enter(GameOver.self)
             gameWon = true
           }
         }  
       }
     }
   ```

#### 3.3：碰撞回调

​	当小球与竹子发生碰撞的时候，竹子应该消失；所以，我们加入下面代码来实现竹子消失的效果

```swift
func breakBlock(node: SKNode) {
    // 创建竹子消失的特效
  let particles = SKEmitterNode(fileNamed: "BrokenPlatform")!
    // 特效出现的位置就是节点的位置
  particles.position = node.position
  particles.zPosition = 3
  addChild(particles)
    // 将特效加入后，删除竹子节点
  particles.run(SKAction.sequence([SKAction.wait(forDuration: 1.0), 
    SKAction.removeFromParent()]))
  node.removeFromParent()
}
```

### 4：游戏玩法

#### 4.1：游戏状态

> 最开始我们定义了三个游戏状态，此时我们把游戏状态加入到这个游戏中

```swift
import GameplayKit
```

> 插入变量在`var isFingerOnPaddle = false:`之后

```swift
lazy var gameState: GKStateMachine = GKStateMachine(states: [
  WaitingForTap(scene: self),
  Playing(scene: self),
  GameOver(scene: self)])
}
```

#### 4.2：游戏初始化

​	在`didMove(to:)`方法下面加入以下代码

```swift
// 创建一个通知消息的节点以表明此时游戏状态处于初始化
let gameMessage = SKSpriteNode(imageNamed: "TapToPlay")
gameMessage.name = GameMessageName
gameMessage.position = CGPoint(x: frame.midX, y: frame.midY)
gameMessage.zPosition = 4
gameMessage.setScale(0.0)
addChild(gameMessage)
    
gameState.enter(WaitingForTap.self)
```

​	在我们新创建的名字为`WaitForTap` 的`swift.file`里面加入以下代码

```swift
// 游戏处于初始化状态时，将提示消息变大
override func didEnter(from previousState: GKState?) {
  let scale = SKAction.scale(to: 1.0, duration: 0.25)
  scene.childNode(withName: GameMessageName)!.run(scale)
}
  // 游戏退出初始化状态时，将提示消息缩小至0
override func willExit(to nextState: GKState) {
  if nextState is Playing {
    let scale = SKAction.scale(to: 0, duration: 0.4)
    scene.childNode(withName: GameMessageName)!.run(scale)
  }
}
```

#### 4.3：游戏进行时

​	在`GameScene.swift`的最后面加入以下代码

```swift
// 此方法是为了能让游戏开始的时候小球的初是弹跳方向具有可变性
func randomFloat(from: CGFloat, to: CGFloat) -> CGFloat {
  let rand: CGFloat = CGFloat(Float(arc4random()) / 0xFFFFFFFF)
  return (rand) * (to - from) + from
}
```

​	在我们新创建的名字为`Playing` 的`swift.file`里面加入以下代码

```swift
// 小球弹跳的方向随机性
func randomDirection() -> CGFloat {
  let speedFactor: CGFloat = 3.0
  if scene.randomFloat(from: 0.0, to: 100.0) >= 50 {
    return -speedFactor
  } else {
    return speedFactor
  }
}
```

​	在`didEnter(from:)`加入以下内容

```swift
// 游戏开始，先给小球添加一个初始的力的方向
if previousState is WaitingForTap {
  let ball = scene.childNode(withName: BallCategoryName) as! SKSpriteNode
  ball.physicsBody!.applyImpulse(CGVector(dx: randomDirection(), dy: randomDirection()))
}
```

​	在`update(deltaTime:)`方法中添加以下内容

```swift
// 设置小球的速度
let ball = scene.childNode(withName: BallCategoryName) as! SKSpriteNode
let maxSpeed: CGFloat = 400.0 
let xSpeed = sqrt(ball.physicsBody!.velocity.dx * ball.physicsBody!.velocity.dx)
let ySpeed = sqrt(ball.physicsBody!.velocity.dy * ball.physicsBody!.velocity.dy)
let speed = sqrt(ball.physicsBody!.velocity.dx * ball.physicsBody!.velocity.dx + ball.physicsBody!.velocity.dy * ball.physicsBody!.velocity.dy)
// 判断小球的速度，如果下降到10，就再次为其添加一个力
if xSpeed <= 10.0 {
  ball.physicsBody!.applyImpulse(CGVector(dx: randomDirection(), dy: 0.0))
}
if ySpeed <= 10.0 {
  ball.physicsBody!.applyImpulse(CGVector(dx: 0.0, dy: randomDirection()))
}
    
if speed > maxSpeed {
  ball.physicsBody!.linearDamping = 0.4
} else {
  ball.physicsBody!.linearDamping = 0.0
}
```

#### 4.4：游戏结束

​	在我们新创建的名字为`GameOver` 的`swift.file`里面的`didEnter(from:)`加入以下代码

```swift
if previousState is Playing {
    //当游戏结束时，给小球添加阻力，使其不能跳动
  let ball = scene.childNode(withName: BallCategoryName) as! SKSpriteNode
  ball.physicsBody!.linearDamping = 1.0
  scene.physicsWorld.gravity = CGVector(dx: 0.0, dy: -9.8)
}
```

